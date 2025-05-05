package com.samplepacks.digital_store.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.entity.Product;
import com.samplepacks.digital_store.repository.LocalUserDAO;
import com.samplepacks.digital_store.service.ProductService;
import com.samplepacks.digital_store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private final UserService userService;
    @Autowired
    private ProductService productService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<LocalUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Admin-only product creation
        return ResponseEntity.ok(productService.saveProduct(product));
    }
}