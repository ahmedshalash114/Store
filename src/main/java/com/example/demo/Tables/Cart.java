package com.example.demo.Tables;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdDate;
    private int totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private Set<LineItem> lineItem;

    public Cart() {
    }
    public Cart(LocalDate createdDate, int totalPrice) {
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
    }
    public Cart(Long id, LocalDate createdDate, int totalPrice) {
        this.id = id;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<LineItem> getLineItem() {
        return lineItem;
    }

    public void setLineItem(Set<LineItem> lineItem) {
        this.lineItem = lineItem;
    }
}
