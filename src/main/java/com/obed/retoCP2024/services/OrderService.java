package com.obed.retoCP2024.services;

import com.obed.retoCP2024.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order createOrder(Order order);

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    Page<Order> getAllOrders(Pageable pageable);

    Page<Order> getOrdersByCustomerName(String customerName, Pageable pageable);

    Page<Order> getOrdersByProductId(Long productId, Pageable pageable);
}
