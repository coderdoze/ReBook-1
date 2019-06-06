/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
    Import Package.
    Load and Register Driver
*/

package ServerPack;

/**
 *
 * @author raj
 */

import Entities.Book;
import Entities.User;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    
    private Connection con;
    private PreparedStatement preparedStatement;
    private static DatabaseManager jdbcc;
    
    private DatabaseManager(){
        
        loadDriver();
        
    }
    
    public static DatabaseManager getJDBCConnection(){
     
        if(jdbcc == null){
            jdbcc = new DatabaseManager();
        }
        
        return jdbcc;
        
    }

    private void loadDriver(){
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found Exception while loading the Drivers");
        }
        
    }

    private void setupConnection() throws SQLException {
        
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rebook_schema","root","mysql123");
            
    }
    
    
    private ResultSet executeQuery(String query) throws SQLException
    {
        setupConnection();
        ResultSet resultSet = null;
        preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    
    

    List<Book> getBookList(String booksQuery) {
        ResultSet rs;
        List<Book> bookList = new ArrayList<Book>();
        try {
            
            rs = executeQuery(booksQuery);
            
            while(rs.next()){
                bookList.add(retrieveBook(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookList;
    }

    void addBookToDatabase(Book book) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        try{
            setupConnection();
            //System.out.println(book.getBookName());
            String query = "insert into Book values (?,?,?,?,?,?,?,?);";
            preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1,book.getBookId());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setDouble(3, book.getUnitPrice());
            preparedStatement.setString(4, book.getPurchaseDate());
            preparedStatement.setInt(5, book.getHandNo());
            preparedStatement.setString(6, book.getSupplierId());
            preparedStatement.setString(7, book.getBookCourse());
            preparedStatement.setString(8, book.getBookProgramme());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    } 

    private Book retrieveBook(ResultSet rs) throws SQLException {
        Book book  = new Book();
        book.setBookId(rs.getString("BookId"));
        book.setBookName(rs.getString("BookName"));
        book.setUnitPrice(rs.getDouble("UnitPrice"));
        book.setPurchaseDate(rs.getDate("Purchase_Date").toString());
        book.setHandNo(rs.getInt("HandNo"));
        book.setSupplierId(rs.getString("SupplierId"));
        book.setBookCourse(rs.getString("Course"));
        book.setBookProgramme(rs.getString("Programme"));
        return book;
    }

    void addUserToDatabase(User user) {

        try{
            setupConnection();
            //System.out.println(book.getBookName());
            String query = "insert into User values (?,?,?,?,?,?,?,?,?,?);";
            preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getDob());
            preparedStatement.setString(5, user.getHashed_passwd());
            preparedStatement.setString(6, user.getDepartment());
            preparedStatement.setString(7, user.getProgramme());
            preparedStatement.setString(8, user.getContact());
            preparedStatement.setDouble(9, user.getRating());
            preparedStatement.setInt(10, user.getNumber_rating());
            
            preparedStatement.executeUpdate();
            
            createCartForUser(user);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void createCartForUser(User user) throws SQLException {
        
        String query = "insert into Cart values (?,?,?,?);";
        preparedStatement = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setString(1, user.getUserId()+"Kart");
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);
        preparedStatement.setString(4, user.getUserId());
        
        preparedStatement.executeUpdate();
    }

    List<User> getUserList(String userQuery) {

        ResultSet rs;
        List<User> userList = new ArrayList<>();
        
        try {
            rs = executeQuery(userQuery);
            
            while(rs.next()){
                userList.add(retrieveUser(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return userList;
    }

    private User retrieveUser(ResultSet rs) throws SQLException {
        
        User user = new User();
        
        user.setUserId(rs.getString("UserId"));
        user.setEmail(rs.getString("Email"));
        user.setName(rs.getString("Name"));
        user.setDob(rs.getDate("User-DOB").toString());
        user.setHashed_passwd(rs.getString("Hashed_Passwd"));
        user.setDepartment(rs.getString("Department"));
        user.setProgramme(rs.getString("Programme"));
        user.setContact(rs.getString("Contact"));
        user.setRating(rs.getDouble("Rating"));
        user.setNumber_rating(rs.getInt("Number_Ratings"));
        return user;
    }

    void addBookToUserCart(User customer, Book cartbook) {
        
        
        try {
            setupConnection();
            
            String query = "insert into Cart_has_Book values (?,?,?,?);";
            
            preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            preparedStatement.setString(1,customer.getUserId()+"Kart");
            preparedStatement.setString(2, customer.getUserId());
            preparedStatement.setString(3, cartbook.getBookId());
            preparedStatement.setString(4, cartbook.getSupplierId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void executeUpdateQueries(String query) throws SQLException{
        
        setupConnection();
        
        preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        preparedStatement.executeUpdate();
        
        
    }
    
    void updateUser(String updateQuery) {

        try {
            executeUpdateQueries(updateQuery);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean checkValidity(String userID, String passwd) {
        
        String query = "select * from User where UserId = \""+userID+"\"";
        ResultSet rs;
        try {
            rs = executeQuery(query);
            if(rs.next()){
                String dbPass = rs.getString("Hashed_Passwd");
                if(passwd.equals(dbPass)){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Couldn't Validate");
        }
        return false;
    }
    
}
