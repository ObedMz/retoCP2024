package com.obed.retoCP2024.services;

import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.exceptions.mappers.ResourceNotFoundException;
import com.obed.retoCP2024.repository.ProductRepository;
import com.obed.retoCP2024.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void testSaveProduct_Success() throws Exception {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(10.0);

        // Mock productRepository behavior
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        when(productRepository.save(product)).thenReturn(product);

        // Call the service method
        Product savedProduct = productService.saveProduct(product);

        // Verify interactions and assertions
        verify(productRepository).findByName(product.getName());
        verify(productRepository).save(product);
        assertEquals(product, savedProduct);
    }
    @Test
    void testSaveProduct_DuplicateName() throws Exception {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(10.0);

        Product existingProduct = new Product();
        existingProduct.setName("Product 1");
        existingProduct.setPrice(20.0);

        // Mock productRepository behavior
        when(productRepository.findByName(product.getName())).thenReturn(Optional.of(existingProduct));

        // Expect exception
        assertThrows(DataIntegrityViolationException.class, () -> productService.saveProduct(product));

        // No interaction with productRepository.save expected
        verify(productRepository, times(0)).save(product);
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        Long productId = 1L;

        // Mock productRepository behavior
        when(productRepository.existsById(productId)).thenReturn(true);

        // Call the service method
        productService.deleteProduct(productId);

        // Verify interactions
        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        Long productId = 1L;

        // Mock productRepository behavior
        when(productRepository.existsById(productId)).thenReturn(false);

        // Expected exception
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));

        // No interaction with productRepository.deleteById expected
        verify(productRepository, times(0)).deleteById(productId);
    }
}
