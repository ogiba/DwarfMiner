/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.insert

import com.mongodb.BasicDBList
import com.mongodb.client.MongoCollection
import com.mongodb.util.JSON
import java.util.ArrayList
import java.util.stream.Collectors
import org.bson.Document

/**
 *
 * @author robertogiba
 */
class InsertDocumentPresenter : IInsertDocumentPresenter {
    override var documentCollection: MongoCollection<Document>? = null

    override fun insertDataToDb(data: String) {
        convertJsonString(data)
    }

    private fun convertJsonString(data: String) {
        val dataToCheck = data.trim { it <= ' ' }

        when {
            dataToCheck.startsWith("{") && dataToCheck.endsWith("}") -> {
                prepareDocumentForData(data)
            }
            dataToCheck.startsWith("[") && dataToCheck.endsWith("]") -> {
                prepareDocumentListForData(data)
            }
            else                                                     -> {
                println("Data has invalid format")
            }
        }
    }

    private fun prepareDocumentForData(data: String) {
        val document = Document.parse(data)
        println(document)
    }

    private fun prepareDocumentListForData(data: String) {
        val parsedData = JSON.parse(data) as BasicDBList

        val properData = parsedData.map { doc -> Document.parse(doc.toString()) }.toList()
        println("Data is json array $properData")
    }
}
