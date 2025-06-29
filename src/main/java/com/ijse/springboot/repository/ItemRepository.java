package com.ijse.springboot.repository;

import com.ijse.springboot.entity.Item;
import com.ijse.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByStatus(Item.Status status);

    List<Item> findByCategory(Item.Category category);

    List<Item> findByReportedBy(User user);

    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    @Query("SELECT i FROM Item i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Item> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT i FROM Item i WHERE i.status = :status AND i.category = :category")
    List<Item> findByStatusAndCategory(@Param("status") Item.Status status, @Param("category") Item.Category category);

    @Query("SELECT i FROM Item i WHERE i.status IN :statuses ORDER BY i.createdAt DESC")
    List<Item> findByStatusInOrderByCreatedAtDesc(@Param("statuses") List<Item.Status> statuses);

    List<Item> findTop10ByOrderByCreatedAtDesc();

    long countByStatus(Item.Status status);
}