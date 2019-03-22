/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.main;

/**
 *
 * @author ogiba
 */
public interface IMainPresenter {
    void checkConnection();
    
    void loadData();
    
    void loadCollections();
    
    void loadSelectedCollection(String collection);
    
    void proceedInsertAction();
}
