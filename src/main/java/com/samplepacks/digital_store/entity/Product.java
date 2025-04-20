package com.samplepacks.digital_store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private String tags;
    private double price;
    private String filePath; // Path to the digital file
    private String imageUrl; // Product image

    // Other fields like category, tags, etc.
}