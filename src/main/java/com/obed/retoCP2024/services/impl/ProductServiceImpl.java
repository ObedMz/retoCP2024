package com.obed.retoCP2024.services.impl;

import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.exceptions.mappers.ResourceNotFoundException;
import com.obed.retoCP2024.repository.ProductRepository;
import com.obed.retoCP2024.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing products.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Saves a new product or updates an existing one.
     *
     * @param product the product to be saved or updated
     * @return the saved product
     * @throws DataIntegrityViolationException if a product with the same name already exists
     */
    @Override
    @Transactional
    public Product saveProduct(Product product) {
        logger.info("Saving product with name: {}", product.getName());

        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()){
            logger.warn("Product already exists with name: {}", product.getName());
            throw new DataIntegrityViolationException("Product already exists with name: " + product.getName());

        }

        Product savedProduct = productRepository.save(product);
        logger.info("Product saved with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the retrieved product
     * @throws ResourceNotFoundException if the product with the given ID is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @throws ResourceNotFoundException if the product with the given ID is not found
     */
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) throw new ResourceNotFoundException("Product not found.");
        productRepository.deleteById(id);
    }
}
