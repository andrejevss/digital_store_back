package com.samplepacks.digital_store.repository;

import com.samplepacks.digital_store.entity.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
