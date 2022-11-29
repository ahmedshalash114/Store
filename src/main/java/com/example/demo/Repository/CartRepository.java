package com.example.demo.Repository;

import com.example.demo.Tables.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
