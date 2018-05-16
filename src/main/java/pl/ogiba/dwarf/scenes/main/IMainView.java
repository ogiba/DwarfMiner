/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.main;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import javafx.scene.Scene;
import org.bson.Document;

/**
 *
 * @author ogiba
 */
public interface IMainView {
    Scene getScene();
    
    void onConnectionResult(boolean isConnected);
    
    void onDataLoaded(String dbName);
    
    void onCollectionsLoaded(ArrayList<String> collections);
    
    void onColectionSelected();
    
    void onSelectedCollectionLoaded(String data);
    
    void onInsertApplied(MongoCollection<Document> document);
}
