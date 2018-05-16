/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentPresenter implements IInsertDocumentPresenter{
    private MongoCollection<Document> collection;

    @Override
    public void transferCollectionReference(MongoCollection<Document> collection) {
        this.collection = collection;
    }  
  
    @Override
    public void insertDataToDb(String data) {
        System.out.println(data);
    }
}
