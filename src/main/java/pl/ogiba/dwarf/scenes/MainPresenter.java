/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.event.ServerClosedEvent;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import pl.ogiba.dwarf.utils.ServerListenerAdapter;
import pl.ogiba.dwarf.utils.ServerMonitorListenerAdapter;

/**
 *
 * @author ogiba
 */
public class MainPresenter implements IMainPresenter {

    private final IMainView mainView;

    private MongoClient client;
    private MongoDatabase db;
    private final ArrayList<String> loadedDBs;

    private boolean isConnected = false;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        this.loadedDBs = new ArrayList<>();
    }

    @Override
    public void checkConnection() {
        if (isConnected) {
            disconnect();
        } else {
            connectToDb();
        }
    }

    @Override
    public void loadData() {
        if (db == null || !isConnected) {
            return;
        }

        final String dbName = db.getName();

        if (!loadedDBs.contains(dbName)) {
            this.loadedDBs.add(dbName);
            mainView.onDataLoaded(dbName);
        }
    }

    @Override
    public void loadCollections() {
        if (db == null || !isConnected) {
            return;
        }

        MongoIterable<String> collections = db.listCollectionNames();
        ArrayList<String> values = collections.into(new ArrayList<>());
        System.err.println(String.format("Number of collections: %s", values.size()));

        mainView.onCollectionsLoaded(values);
    }

    @Override
    public void loadSelectedCollection(String collection) {
        MongoCollection<Document> selectedCollection = db.getCollection(collection);

        System.out.println(selectedCollection.find().first());

        String data = JSON.serialize(selectedCollection.find());
        mainView.onSelectedCollectionLoaded(data);
    }

    private void connectToDb() {
        MongoClientOptions clientOptions = new MongoClientOptions.Builder()
                .addServerListener(new ServerListenerAdapter() {
                    @Override
                    public void serverClosed(ServerClosedEvent event) {
                        isConnected = false;
                        mainView.onConnectionResult(isConnected);
                    }
                })
                .addServerMonitorListener(new ServerMonitorListenerAdapter() {
                    @Override
                    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent event) {
                        super.serverHeartbeatFailed(event);

                        isConnected = false;
                        mainView.onConnectionResult(isConnected);
                    }

                })
                .build();

        ServerAddress serverAddress = new ServerAddress("127.0.0.1", 27017);

        client = new MongoClient(serverAddress, clientOptions);

        db = client.getDatabase("dwarf_test");

        isConnected = true;

        mainView.onConnectionResult(isConnected);
    }

    private void disconnect() {
        client.close();
        client = null;
        db = null;
    }
}
