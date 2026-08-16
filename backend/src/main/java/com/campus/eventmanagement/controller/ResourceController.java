package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.entity.Resource;
import com.campus.eventmanagement.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ADMIN only (enforced in SecurityConfig) — manages the pool of bookable venues/equipment
@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceRepository resourceRepository;

    @GetMapping
    public ResponseEntity<List<Resource>> getAll() {
        return ResponseEntity.ok(resourceRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Resource> create(@RequestBody Resource resource) {
        return ResponseEntity.ok(resourceRepository.save(resource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resourceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
