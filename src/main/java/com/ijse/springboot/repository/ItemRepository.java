package com.ijse.springboot.repository;

import com.ijse.springboot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
