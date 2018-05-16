/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.Console;
import org.bson.Document;
import sun.rmi.runtime.Log;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentPresenter implements IInsertDocumentPresenter{
    private MongoCollection<Document> mongoCollection;

    @Override
    public void transferCollectionReference(MongoCollection<Document> collection) {
        this.mongoCollection = collection;
    }  
  
    @Override
    public void insertDataToDb(String data) {
        System.out.println(data);
        
        Document document = Document.parse(data);
        System.out.println(document);
    }
}
