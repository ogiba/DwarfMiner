/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.utils;

import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;

/**
 *
 * @author robertogiba
 */
public abstract class ServerMonitorListenerAdapter implements ServerMonitorListener {

    @Override
    public void serverHearbeatStarted(ServerHeartbeatStartedEvent event) {
        //Override to achive expected behavior
    }

    @Override
    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent event) {
        //Override to achive expected behavior
    }

    @Override
    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent event) {
        //Override to achive expected behavior
    }
}
