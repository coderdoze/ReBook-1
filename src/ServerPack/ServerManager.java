/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPack;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author raj
 */
public class ServerManager {

    private ConnectionRecceiver connectionRecceiver;
    protected static Set<ClientConnection> clientConnections = new HashSet<ClientConnection>();
    
    
    ServerManager() {
    }

    private void startServer(int port){
        connectionRecceiver = new ConnectionRecceiver(port);
    }
    
    public static void main(String args[]){
        ServerManager serverManager  = new ServerManager();
        serverManager.startServer(4208);
    }
    
}
