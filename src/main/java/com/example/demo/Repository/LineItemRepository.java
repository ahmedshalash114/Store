package com.example.demo.Repository;

import com.example.demo.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem,Long> {
}
