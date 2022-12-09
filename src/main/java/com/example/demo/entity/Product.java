package com.example.demo.entity;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String description;

    @OneToMany(mappedBy = "product")
    private Set<LineItem> lineItem;

    public Product() {
    }
    public Product(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public Product(Long id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<LineItem> getLineItem() {
        return lineItem;
    }

    public void setLineItem(Set<LineItem> lineItem) {
        this.lineItem = lineItem;
    }
}
