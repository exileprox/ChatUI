/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import g53sqm.chat.client.Client;

/**
 *
 * @author Chris
 */
public class SessionObject {

    private static SessionObject instance = null;
    private volatile Client client;

    private SessionObject() {
    }

    ;
    
    public static synchronized SessionObject getInstance() {
        if (instance == null) {
            synchronized (SessionObject.class) {
                if (instance == null) {
                    instance = new SessionObject();
                }
            }
        }
        return instance;
    }

    public synchronized Client getClient() {
        synchronized (this) {
            return client;
        }
    }

    public void setClient(Client client) {
        synchronized (this) {
            this.client = client;
        }
    }

}
