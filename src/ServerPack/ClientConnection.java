/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPack;

import Entities.Book;
import Entities.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raj
 */
public class ClientConnection implements Runnable {

    private ServerManager serverManager;
    private Socket serverSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DatabaseManager databaseManager;
    private volatile boolean running;
    
    public ClientConnection(Socket ss)
    {
        try {
            this.serverSocket = ss;
            dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(dataOutputStream);
            dataInputStream = new DataInputStream((serverSocket.getInputStream()));
            objectInputStream = new ObjectInputStream(dataInputStream);
            databaseManager = DatabaseManager.getJDBCConnection();
            this.running = true;
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void run(){
        
        work();
        
        
    }

    private void work() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
               
        while(running){
            
            try{
                
                System.out.println("Waiting for Code");
                int code = Integer.parseInt(dataInputStream.readUTF());
                System.out.println("The code is "+code);
                
                switch(code){
                    case 0://Selecting Books from the Database
                        String booksQuery = dataInputStream.readUTF();
                        List<Book> books = databaseManager.getBookList(booksQuery);
                        objectOutputStream.writeObject(books);
                        break;
                    case 1://Inserting Book into the Database
                        Book book = (Book) objectInputStream.readObject();
                        databaseManager.addBookToDatabase(book);
                        break;
                    case 2://Inserting User into the Database
                        User user = (User) objectInputStream.readObject();
                        databaseManager.addUserToDatabase(user);
                        break;
                    case 3://Selecting Users from the Database
                        String userQuery = dataInputStream.readUTF();
                        List<User> users = databaseManager.getUserList(userQuery);
                        objectOutputStream.writeObject(users);
                        break;
                    case 4://Adding to cart of a User
                        User customer = (User) objectInputStream.readObject();
                        Book cartbook = (Book) objectInputStream.readObject();
                        databaseManager.addBookToUserCart(customer,cartbook);
                        break;
                    case 5://Update User
                        String updateQuery = dataInputStream.readUTF();
                        databaseManager.updateUser(updateQuery);
                        break;
                    case 6://Validate User
                        System.out.println("Receiving");
                        String userID = dataInputStream.readUTF();
                        System.out.println("Userid: "+userID);
                        String passwd = dataInputStream.readUTF();
                        System.out.println("Received");
                        boolean auth = databaseManager.checkValidity(userID, passwd);
                        dataOutputStream.writeBoolean(auth);
                        break;
                    case -999:
                        disconnectClient();
                }
                
            }catch(Exception e){
                e.printStackTrace();
                this.running = false;
                try {
                    disconnectClient();
                } catch (IOException ex) {
                    System.out.println("Unable to Close Streams");
                }
            }
        }
        
        
    
    }

    private void disconnectClient() throws IOException {
        this.running = false;
        System.out.println("Client Disconnected");
        dataInputStream.close();
        dataOutputStream.close();
        objectInputStream.close();
        objectOutputStream.close();
        ServerManager.clientConnections.remove(this);
        
    }
    
}
