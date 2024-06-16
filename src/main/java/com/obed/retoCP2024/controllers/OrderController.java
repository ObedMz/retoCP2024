package com.obed.retoCP2024.controllers;

import com.obed.retoCP2024.entities.Order;
import com.obed.retoCP2024.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(Pageable pageable){
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }


    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id){
        orderService.deleteOrder(id);
    }

}
