/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.main

import com.mongodb.client.MongoCollection
import java.util.ArrayList
import javafx.scene.Scene
import org.bson.Document

/**
 *
 * @author ogiba
 */
interface IMainView {
    fun getScene(): Scene

    fun onConnectionResult(isConnected: Boolean)

    fun onDataLoaded(dbName: String)

    fun onCollectionsLoaded(collections: ArrayList<String>)

    fun onColectionSelected()

    fun onSelectedCollectionLoaded(data: String)

    fun onInsertApplied(document: MongoCollection<Document>)
}
