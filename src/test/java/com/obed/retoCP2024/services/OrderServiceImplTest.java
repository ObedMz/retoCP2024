package com.obed.retoCP2024.services;

import com.obed.retoCP2024.entities.Order;
import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.exceptions.mappers.ResourceNotFoundException;
import com.obed.retoCP2024.repository.OrderRepository;
import com.obed.retoCP2024.repository.ProductRepository;
import com.obed.retoCP2024.services.impl.OrderServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void testCreateOrder_Success() {
        // Mock product and order data
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        Order order = new Order();
        order.setProduct(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.createOrder(order);

        verify(productRepository).findById(productId);
        verify(orderRepository).save(order);
        assertEquals(product, savedOrder.getProduct());
    }

    @Test
    void testDeleteOrder_Success() {
        Long orderId = 1L;

        // Mock orderRepository behavior
        when(orderRepository.existsById(orderId)).thenReturn(true);

        // Call the service method
        orderService.deleteOrder(orderId);

        // Verify interactions
        verify(orderRepository).existsById(orderId);
        verify(orderRepository).deleteById(orderId);
    }
    @Test
    void testDeleteOrder_NotFound() throws Exception {
        Long orderId = 1L;

        // Mock orderRepository behavior
        when(orderRepository.existsById(orderId)).thenReturn(false);

        // Expected exception
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(orderId));

        // No interaction with orderRepository.deleteById expected
        verify(orderRepository, times(0)).deleteById(orderId);
    }

}
