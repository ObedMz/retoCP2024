package com.obed.retoCP2024.services.impl;

import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.exceptions.ResourceNotFoundException;
import com.obed.retoCP2024.repository.ProductRepository;
import com.obed.retoCP2024.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    @Transactional
    public Product saveProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent())
            throw new DataIntegrityViolationException("Product already exists with name: " + product.getName());

        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) throw new ResourceNotFoundException("Product not found.");
        productRepository.deleteById(id);
    }
}
