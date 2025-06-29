package com.ijse.springboot.repository;

import com.ijse.springboot.entity.Request;
import com.ijse.springboot.entity.User;
import com.ijse.springboot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByStatus(Request.Status status);

    List<Request> findByUser(User user);

    List<Request> findByItem(Item item);

    List<Request> findByUserAndStatus(User user, Request.Status status);

    List<Request> findByItemAndStatus(Item item, Request.Status status);

    @Query("SELECT r FROM Request r WHERE r.user = :user ORDER BY r.createdAt DESC")
    List<Request> findByUserOrderByCreatedAtDesc(@Param("user") User user);

    @Query("SELECT r FROM Request r WHERE r.status = :status ORDER BY r.createdAt ASC")
    List<Request> findByStatusOrderByCreatedAtAsc(@Param("status") Request.Status status);

    long countByStatus(Request.Status status);

    long countByUser(User user);

    boolean existsByUserAndItem(User user, Item item);
}