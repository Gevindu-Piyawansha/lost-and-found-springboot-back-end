package com.ijse.springboot.controller;

import com.ijse.springboot.entity.Request;
import com.ijse.springboot.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @PostMapping
    public Request createRequest(@RequestBody Request request) {
        return requestRepository.save(request);
    }

    @PutMapping("/{id}")
    public Request updateRequest(@PathVariable Long id, @RequestBody Request updated) {
        Request existing = requestRepository.findById(id).orElseThrow();
        existing.setStatus(updated.getStatus());
        existing.setItem(updated.getItem());
        existing.setUser(updated.getUser());
        return requestRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestRepository.deleteById(id);
    }
}
