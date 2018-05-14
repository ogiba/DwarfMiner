/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.client.MongoDatabase;
import javafx.scene.Scene;

/**
 *
 * @author robertogiba
 */
public interface IInsertDocumentView {
    Scene getScene();
    
    void setDatabaseReference(MongoDatabase database);
    
    void show();
}
