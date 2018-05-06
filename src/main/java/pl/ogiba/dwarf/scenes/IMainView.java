/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes;

import java.util.ArrayList;
import javafx.scene.Scene;

/**
 *
 * @author ogiba
 */
public interface IMainView {
    Scene getScene();
    
    void onConnectionResult(boolean isConnected);
    
    void onDataLoaded(String dbName);
    
    void onCollectionLoaded(ArrayList<String> collections);
}
