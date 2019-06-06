/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raj
 */
public class ConnectionRecceiver implements Runnable{
   
    
    ServerSocket serverSocket;

    public ConnectionRecceiver( int port) {
        
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionRecceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Thread t = new Thread(this);
        t.start();
    }
    
    
    

    public void getConnections(){
        
        while(true){

            Socket socket = null;
            
            try {
                System.out.println("Waiting for Client");
                socket = serverSocket.accept();
                ClientConnection cc = new ClientConnection(socket);
                ServerManager.clientConnections.add(cc);
                System.out.println("Client Connected");
                Thread t = new Thread(cc);
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionRecceiver.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        getConnections();
    }
    
    
    
}
