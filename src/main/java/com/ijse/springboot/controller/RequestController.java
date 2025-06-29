package com.ijse.springboot.controller;

import com.ijse.springboot.entity.Request;
import com.ijse.springboot.entity.User;
import com.ijse.springboot.entity.Item;
import com.ijse.springboot.repository.RequestRepository;
import com.ijse.springboot.repository.UserRepository;
import com.ijse.springboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestRepository.findAll();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<Request>> getMyRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            List<Request> myRequests = requestRepository.findByUser(user.get());
            return ResponseEntity.ok(myRequests);
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Request>> getPendingRequests() {
        List<Request> pendingRequests = requestRepository.findByStatus(Request.Status.PENDING);
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Request>> getApprovedRequests() {
        List<Request> approvedRequests = requestRepository.findByStatus(Request.Status.APPROVED);
        return ResponseEntity.ok(approvedRequests);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<Request>> getRejectedRequests() {
        List<Request> rejectedRequests = requestRepository.findByStatus(Request.Status.REJECTED);
        return ResponseEntity.ok(rejectedRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            request.setUser(user.get());
            request.setStatus(Request.Status.PENDING); // Set default status
            Request savedRequest = requestRepository.save(request);
            return ResponseEntity.ok(savedRequest);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/item/{itemId}")
    public ResponseEntity<Request> createRequestForItem(@PathVariable Long itemId, @RequestBody Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        Optional<Item> item = itemRepository.findById(itemId);

        if (user.isPresent() && item.isPresent()) {
            request.setUser(user.get());
            request.setItem(item.get());
            request.setStatus(Request.Status.PENDING);
            Request savedRequest = requestRepository.save(request);
            return ResponseEntity.ok(savedRequest);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request updated) {
        Optional<Request> existingRequest = requestRepository.findById(id);
        if (existingRequest.isPresent()) {
            Request existing = existingRequest.get();
            existing.setStatus(updated.getStatus());
            existing.setMessage(updated.getMessage());
            existing.setAdminResponse(updated.getAdminResponse());

            Request updatedRequest = requestRepository.save(existing);
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Request> approveRequest(@PathVariable Long id,
            @RequestBody(required = false) String adminResponse) {
        Optional<Request> existingRequest = requestRepository.findById(id);
        if (existingRequest.isPresent()) {
            Request existing = existingRequest.get();
            existing.setStatus(Request.Status.APPROVED);
            if (adminResponse != null) {
                existing.setAdminResponse(adminResponse);
            }

            // Update item status to CLAIMED if request is approved
            if (existing.getItem() != null) {
                Item item = existing.getItem();
                item.setStatus(Item.Status.RETURNED);
                itemRepository.save(item);
            }

            Request updatedRequest = requestRepository.save(existing);
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Request> rejectRequest(@PathVariable Long id,
            @RequestBody(required = false) String adminResponse) {
        Optional<Request> existingRequest = requestRepository.findById(id);
        if (existingRequest.isPresent()) {
            Request existing = existingRequest.get();
            existing.setStatus(Request.Status.REJECTED);
            if (adminResponse != null) {
                existing.setAdminResponse(adminResponse);
            }

            Request updatedRequest = requestRepository.save(existing);
            return ResponseEntity.ok(updatedRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}