package com.obed.retoCP2024.services;

import com.obed.retoCP2024.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    public Product saveProduct(Product product);

    public List<Product> getAllProducts();

    public Optional<Product> getProductById(Long id);

    public void deleteProduct(Long id);
}
