package com.ijse.springboot.controller;

import com.ijse.springboot.entity.Item;
import com.ijse.springboot.entity.Request;
import com.ijse.springboot.repository.ItemRepository;
import com.ijse.springboot.repository.RequestRepository;
import com.ijse.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/stats")
public ResponseEntity<Map<String, Object>> getDashboardStats() {
    Map<String, Object> stats = new HashMap<>();

    // Item statistics
    stats.put("totalItems", itemRepository.count());
    stats.put("lostItems", itemRepository.countByStatus(Item.Status.LOST));
    stats.put("foundItems", itemRepository.countByStatus(Item.Status.FOUND));
    stats.put("returnedItems", itemRepository.countByStatus(Item.Status.RETURNED));

    // Request statistics
    stats.put("totalRequests", requestRepository.count());
    stats.put("pendingRequests", requestRepository.countByStatus(Request.Status.PENDING));
    stats.put("approvedRequests", requestRepository.countByStatus(Request.Status.APPROVED));
    stats.put("rejectedRequests", requestRepository.countByStatus(Request.Status.REJECTED));

    // User statistics
    stats.put("totalUsers", userRepository.count());

    return ResponseEntity.ok(stats);
}
}