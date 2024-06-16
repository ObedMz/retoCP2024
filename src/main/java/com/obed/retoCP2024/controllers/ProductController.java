package com.obed.retoCP2024.controllers;

import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

/**
 * Controller class for handling operations related to products.
 */
@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Endpoint to create or update a product.
     *
     * <p>Creates a new product if it doesn't exist or updates an existing product based on the provided
     * request body. Returns HTTP status 201 (Created) with the created or updated product in the response body.</p>
     *
     * @param product the product object to be created or updated
     * @return ResponseEntity containing the created or updated product and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Product> createOrUpdateProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }

    /**
     * Endpoint to retrieve all products.
     *
     * <p>Returns HTTP status 200 (OK) with a list of all products in the response body.</p>
     *
     * @return ResponseEntity containing a list of products and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Endpoint to retrieve a product by its ID.
     *
     * <p>Returns HTTP status 200 (OK) with the product matching the provided ID in the response body.</p>
     *
     * @param id the ID of the product to retrieve
     * @return ResponseEntity containing the product with HTTP status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Endpoint to delete a product by its ID.
     *
     * <p>Returns HTTP status 204 (No Content) if the product is successfully deleted.</p>
     *
     * @param id the ID of the product to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
