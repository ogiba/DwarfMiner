/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.main

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.ServerAddress
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.event.ServerClosedEvent
import com.mongodb.event.ServerHeartbeatFailedEvent
import com.mongodb.util.JSON
import org.bson.Document
import pl.ogiba.dwarf.utils.ServerListenerAdapter
import pl.ogiba.dwarf.utils.ServerMonitorListenerAdapter
import java.util.*

/**
 *
 * @author ogiba
 */
class MainPresenter(private val mainView: IMainView) : IMainPresenter {

    private var client: MongoClient? = null
    private var db: MongoDatabase? = null
    private val loadedDBs: MutableList<String> = mutableListOf()
    private var selectedCollection: MongoCollection<Document>? = null

    private var isConnected = false

    override fun checkConnection() {
        if (isConnected) {
            disconnect()
        } else {
            connectToDb()
        }
    }

    override fun loadData() {
        db?.takeIf { isConnected }?.run {
            loadedDBs.takeIf { !it.contains(name) }?.run {
                add(name)
                mainView.onDataLoaded(name)
            }
        }
    }

    override fun loadCollections() {
        db?.takeIf { isConnected }?.run {
            val collections = listCollectionNames()
            val values = collections.into(ArrayList())
            System.err.println(String.format("Number of collections: %s", values.size))
            mainView.onCollectionsLoaded(values)
        }
    }

    override fun loadSelectedCollection(collection: String) {
        mainView.onColectionSelected()
        selectedCollection = db?.getCollection(collection)?.also { selected ->
            println(selected.find().first())
            mainView.onSelectedCollectionLoaded(JSON.serialize(selected.find()))
        }
    }

    override fun proceedInsertAction() {
        mainView.onInsertApplied(selectedCollection!!)
    }

    private fun connectToDb() {
        val clientOptions = MongoClientOptions.Builder().addServerListener(object : ServerListenerAdapter() {
            override fun serverClosed(event: ServerClosedEvent) {
                isConnected = false
                mainView.onConnectionResult(isConnected)
            }
        }).addServerMonitorListener(object : ServerMonitorListenerAdapter() {
            override fun serverHeartbeatFailed(event: ServerHeartbeatFailedEvent) {
                super.serverHeartbeatFailed(event)

                isConnected = false
                mainView.onConnectionResult(isConnected)
            }

        }).build()

        val serverAddress = ServerAddress("127.0.0.1", 27017)

        client = MongoClient(serverAddress, clientOptions).also { newClient ->
            db = newClient.getDatabase("dwarf_test")
            isConnected = true
        }
        mainView.onConnectionResult(isConnected)
    }

    private fun disconnect() {
        client?.close()
        client = null
        db = null
    }
}
