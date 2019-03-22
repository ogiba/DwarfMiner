/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.scenes.main

/**
 *
 * @author ogiba
 */
interface IMainPresenter {
    fun checkConnection()

    fun loadData()

    fun loadCollections()

    fun loadSelectedCollection(collection: String)

    fun proceedInsertAction()
}
