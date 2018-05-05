/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author ogiba
 */
public class MainPresenter implements IMainPresenter {

    private IMainView mainView;

    private MongoClient client;
    private MongoDatabase db;

    private boolean isConnected = false;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void checkConnection() {
        if (isConnected) {
            isConnected = false;
            disconnect();
        } else {
            connectToDb();
            isConnected = true;
        }
        
        mainView.onConnectionResult(isConnected);
    }

    private void connectToDb() {
        client = new MongoClient("127.0.0.1", 27017);
        db = client.getDatabase("dwarf_test");
    }

    private void disconnect() {
        client.close();
        client = null;
        db = null;
    }
}
