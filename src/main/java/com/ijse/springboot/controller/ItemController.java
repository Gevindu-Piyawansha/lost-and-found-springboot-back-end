package com.ijse.springboot.controller;

import com.ijse.springboot.entity.Item;
import com.ijse.springboot.entity.User;
import com.ijse.springboot.repository.ItemRepository;
import com.ijse.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/lost")
    public ResponseEntity<List<Item>> getLostItems() {
        List<Item> lostItems = itemRepository.findByStatus(Item.Status.LOST);
        return ResponseEntity.ok(lostItems);
    }

    @GetMapping("/found")
    public ResponseEntity<List<Item>> getFoundItems() {
        List<Item> foundItems = itemRepository.findByStatus(Item.Status.FOUND);
        return ResponseEntity.ok(foundItems);
    }

    @GetMapping("/claimed")
    public ResponseEntity<List<Item>> getClaimedItems() {
        List<Item> claimedItems = itemRepository.findByStatus(Item.Status.CLAIMED);
        return ResponseEntity.ok(claimedItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            item.setReportedBy(user.get());
        }

        Item savedItem = itemRepository.save(item);
        return ResponseEntity.ok(savedItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        Optional<Item> existingItem = itemRepository.findById(id);
        if (existingItem.isPresent()) {
            Item existing = existingItem.get();
            existing.setName(item.getName());
            existing.setDescription(item.getDescription());
            existing.setStatus(item.getStatus());
            existing.setLocation(item.getLocation());
            existing.setCategory(item.getCategory());
            existing.setContactInfo(item.getContactInfo());

            Item updatedItem = itemRepository.save(existing);
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Item> updateItemStatus(@PathVariable Long id, @RequestParam Item.Status status) {
        Optional<Item> existingItem = itemRepository.findById(id);
        if (existingItem.isPresent()) {
            Item existing = existingItem.get();
            existing.setStatus(status);
            Item updatedItem = itemRepository.save(existing);
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(@RequestParam String keyword) {
        List<Item> items = itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,
                keyword);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/my-items")
    public ResponseEntity<List<Item>> getMyItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            List<Item> myItems = itemRepository.findByReportedBy(user.get());
            return ResponseEntity.ok(myItems);
        }
        return ResponseEntity.ok(List.of());
    }
}