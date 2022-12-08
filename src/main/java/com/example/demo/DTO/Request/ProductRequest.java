package com.example.demo.DTO.Request;

public class ProductRequest {
    private String name;
    private int price;

    public void setName(String name) {this.name = name;}
    public void setPrice(int price) {this.price = price;}
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
}
