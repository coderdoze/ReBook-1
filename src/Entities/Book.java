package Entities;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raj
 */
public class Book implements Serializable{
    
    private String bookId;
    private String bookName;
    private double unitPrice;
    private String purchaseDate;
    private int handNo;
    private String supplierId;
    private int edition;
    private String bookProgramme;
    private String bookCourse;
    private BufferedImage bookImage1;
    private BufferedImage bookImage2;
    private BufferedImage bookImage3;
    private boolean bookSold;

    public boolean isBookSold() {
        return bookSold;
    }

    public void setBookSold(boolean bookSold) {
        this.bookSold = bookSold;
    }

    public String getBookProgramme() {
        return bookProgramme;
    }

    public void setBookProgramme(String bookProgramme) {
        this.bookProgramme = bookProgramme;
    }

    public String getBookCourse() {
        return bookCourse;
    }

    public void setBookCourse(String bookCourse) {
        this.bookCourse = bookCourse;
    }

    public BufferedImage getBookImage1() {
        return bookImage1;
    }

    public void setBookImage1(BufferedImage bookImage1) {
        this.bookImage1 = bookImage1;
    }

    public BufferedImage getBookImage2() {
        return bookImage2;
    }

    public void setBookImage2(BufferedImage bookImage2) {
        this.bookImage2 = bookImage2;
    }

    public BufferedImage getBookImage3() {
        return bookImage3;
    }

    public void setBookImage3(BufferedImage bookImage3) {
        this.bookImage3 = bookImage3;
    }
    

    public Book() {
    }

    public Book(String bookId, String bookName, double unitPrice, String purchaseDate,
            int handNo, String supplierId, String bookCourse,String bookProgramme, int edition,boolean sold,
            BufferedImage bookImage1,BufferedImage bookImage2,BufferedImage bookImage3) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
        this.purchaseDate = purchaseDate;
        this.handNo = handNo;
        this.supplierId = supplierId;
        this.bookCourse = bookCourse;
        this.bookProgramme=bookProgramme;
        this.edition = edition;
        this.bookSold=sold;
        this.bookImage1=bookImage1;
        this.bookImage2=bookImage2;
        this.bookImage3=bookImage3;
    }

    
    public void setAll(String bookId, String bookName, double unitPrice,
            String purchaseDate, int handNo, String supplierId, String bookCourse,String bookProgramme, int edition,
            boolean sold,BufferedImage bookImage1,BufferedImage bookImage2,BufferedImage bookImage3){
        
        this.bookId = bookId;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
        this.purchaseDate = purchaseDate;
        this.handNo = handNo;
        this.supplierId = supplierId;
        this.bookCourse = bookCourse;
        this.bookProgramme=bookProgramme;
        this.edition = edition;
        this.bookSold=sold;
        this.bookImage1=bookImage1;
        this.bookImage2=bookImage2;
        this.bookImage3=bookImage3;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getEdition() {
        return edition;
    }
    
    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public int getHandNo() {
        return handNo;
    }

    public String getSupplierId() {
        return supplierId;
    }


    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setHandNo(int handNo) {
        this.handNo = handNo;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    
    
    
}
