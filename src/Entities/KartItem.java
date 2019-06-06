/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author mp
 */
public class KartItem implements Serializable{
     
     private String book_name;
     private String seller_name;
     private double price;
     private String sold;

    public KartItem(String book_name, String seller_name, double price,String sold) {
        this.book_name = book_name;
        this.seller_name = seller_name;
        this.price = price;
        this.sold=sold;
     }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public double getPrice() {
        return price;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
}
