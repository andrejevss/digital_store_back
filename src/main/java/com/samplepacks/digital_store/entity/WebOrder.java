package com.samplepacks.digital_store.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "web_order")
@Data
public class WebOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> quantities = new ArrayList<>();
}