package com.samplepacks.digital_store.entity;

import jakarta.persistence.*;
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
    @Column(name = "image_url")
    private String imageUrl; // Product image

    // Other fields like category, tags, etc.
}