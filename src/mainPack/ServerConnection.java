/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPack;

import Entities.Book;
import Entities.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lokesh
 */
public class ServerConnection {
    
    private static ServerConnection serverConnection;
    private String serverIP;
    private Socket s;
    DataOutputStream dos;
    ObjectOutputStream os;
    ObjectInputStream ois;
    DataInputStream dis;
    private ServerConnection(String ip)
    {
        this.serverIP = ip;
        try{
        
        s=new Socket();
        s.connect(new InetSocketAddress(serverIP,HomePage.port),1000);
        dos=new DataOutputStream(s.getOutputStream());
        os=new ObjectOutputStream(s.getOutputStream());
        ois=new ObjectInputStream(s.getInputStream());
        dis=new DataInputStream(s.getInputStream());
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Can not connect to server!",
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    public static ServerConnection getInstance(String ip) throws IOException{
        
        if(serverConnection == null)
        {
            serverConnection = new ServerConnection(ip);
        }
        
        return serverConnection;
    }
    public void saveBook(Book book){
        try{
    
//        dos.write(1);
        dos.writeUTF("1");
        dos.flush();
        os.writeObject(book);
        os.flush();
        JOptionPane.showMessageDialog(null,"Book details saved!","Success",JOptionPane.OK_OPTION);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Could not save details",
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public ArrayList<Book> getList(String selected_category,String selected_programme) throws ClassNotFoundException{
//        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        ArrayList<Book> bookList = new ArrayList<>();

        try{
            dos.writeUTF("0");
            dos.flush();
    //        dos.close();
            String query="SELECT * FROM Book WHERE Course=\""+selected_category+"\" and Programme=\""+selected_programme+"\"";
            dos.writeUTF(query);
            dos.flush();
            bookList =(ArrayList<Book>) ois.readObject();
            return bookList;
        
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Could not save details",
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return bookList;
    }
    public void addUser(User user){
        try{
        dos.writeUTF("2");
        dos.flush();
        os.writeObject(user);
        JOptionPane.showMessageDialog(null,"User details saved!","Success",JOptionPane.OK_OPTION);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not save details",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updateUser(String query){
        try{
            dos.writeUTF("5");
        dos.flush();
        dos.writeUTF(query);
        JOptionPane.showMessageDialog(null,"User details saved!","Success",JOptionPane.OK_OPTION);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not save details",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public User confirmLogin(String login,String pass){
        try{
        dos.writeUTF("6");
        dos.flush();
        dos.writeUTF(login);
        dos.flush();
        dos.writeUTF(pass);
        dos.flush();
        
        return (User)ois.readObject();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not get details",
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public void addToCart(User user,ArrayList<Book>bookList){
        try {
            dos.writeUTF("4");
            dos.flush();
            os.writeObject(user);
            os.flush();
            os.writeObject(bookList);
            System.out.println("Books saved to user cart");
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public ArrayList<Book> getKartItem(String userId){
        try {
            //query
            dos.writeUTF("8");
            dos.flush();
            dos.writeUTF(userId);
            dos.flush();
            ArrayList<Book> books=(ArrayList<Book>)ois.readObject();
            return books;
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void deleteItems(String userId,ArrayList<Book>bookList){
        try {
            dos.writeUTF("7");
            dos.flush();
            dos.writeUTF(userId);
            dos.flush();
            os.writeObject(bookList);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean buy_selected(User user){
        try {
            dos.writeUTF("9");
            dos.flush();
            os.writeObject(user);
            os.flush();
            return dis.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
