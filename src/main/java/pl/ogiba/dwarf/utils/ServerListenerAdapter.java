/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.utils;

import com.mongodb.event.ServerClosedEvent;
import com.mongodb.event.ServerDescriptionChangedEvent;
import com.mongodb.event.ServerListener;
import com.mongodb.event.ServerOpeningEvent;

/**
 *
 * @author robertogiba
 */
public class ServerListenerAdapter implements ServerListener {

    @Override
    public void serverOpening(ServerOpeningEvent event) {
        //Override to use
    }

    @Override
    public void serverClosed(ServerClosedEvent event) {
        //Override to use
    }

    @Override
    public void serverDescriptionChanged(ServerDescriptionChangedEvent event) {
        //Override to use
    }

}
