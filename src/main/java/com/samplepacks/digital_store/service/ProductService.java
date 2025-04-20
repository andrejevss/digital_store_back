package com.samplepacks.digital_store.service;

import com.samplepacks.digital_store.entity.Product;
import com.samplepacks.digital_store.repository.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;

    public List<Product> getAllProducts(){
        return productDAO.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productDAO.findById(id);
    }
    public Product saveProduct(Product product){
        return productDAO.save(product);
    }
    public void deleteProduct(Long id){
        productDAO.deleteById(id);
    }
}
