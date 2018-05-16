/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 *
 * @author robertogiba
 */
public interface IInsertDocumentPresenter {
    void transferCollectionReference(MongoCollection<Document> collection);
    
    void insertDataToDb(String data);
}
