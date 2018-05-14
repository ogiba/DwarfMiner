/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.client.MongoDatabase;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentPresenter implements IInsertDocumentPresenter{
    private MongoDatabase database;
    
    @Override
    public void transferDatabaseReference(MongoDatabase database) {
        this.database = database;
    }
   
}
