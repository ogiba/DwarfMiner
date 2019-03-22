/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert;

import com.mongodb.BasicDBList;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 *
 * @author robertogiba
 */
public class InsertDocumentPresenter implements IInsertDocumentPresenter {

    private MongoCollection<Document> mongoCollection;

    @Override
    public void transferCollectionReference(MongoCollection<Document> collection) {
        this.mongoCollection = collection;
    }

    @Override
    public void insertDataToDb(String data) {
        convertJsonString(data);
    }

    private void convertJsonString(String data) {
        final String dataToCheck = data.trim();

        if (dataToCheck.startsWith("{") && dataToCheck.endsWith("}")) {
            prepareDocumentForData(data);
        } else if (dataToCheck.startsWith("[") && dataToCheck.endsWith("]")) {
            prepareDocumentListForData(data);
        } else {
            System.out.println("Data has invalid format");
        }
    }

    private void prepareDocumentForData(String data) {
        Document document = Document.parse(data);
        System.out.println(document);
    }
    
     private void prepareDocumentListForData(String data) {
         BasicDBList parsedData = (BasicDBList)JSON.parse(data);
         
        final List<Document> properData = parsedData.stream().map((doc) -> { 
            return Document.parse(doc.toString());
        }).collect(Collectors.toList());
        System.out.println("Data is json array");
    }
}
