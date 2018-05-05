/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.event.ServerClosedEvent;
import com.mongodb.event.ServerDescriptionChangedEvent;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerListener;
import com.mongodb.event.ServerOpeningEvent;
import pl.ogiba.dwarf.utils.ServerListenerAdapter;
import pl.ogiba.dwarf.utils.ServerMonitorListenerAdapter;

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
            disconnect();
        } else {
            connectToDb();
        }
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

                    @Override
                    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent event) {
                        super.serverHeartbeatSucceeded(event);

//                        db = client.getDatabase("dwarf_test");
                        isConnected = true;

                        mainView.onConnectionResult(isConnected);
                    }

                })
                .build();

        ServerAddress serverAddress = new ServerAddress("127.0.0.1", 27017);

        client = new MongoClient(serverAddress, clientOptions);
    }

    private void disconnect() {
        client.close();
        client = null;
        db = null;
    }
}
