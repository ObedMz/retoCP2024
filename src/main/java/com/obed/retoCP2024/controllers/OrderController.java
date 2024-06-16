package com.obed.retoCP2024.controllers;

import com.obed.retoCP2024.entities.Order;
import com.obed.retoCP2024.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(@RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<Order>> getOrdersByProductId(@RequestParam(required = false) Pageable pageable,
                                                            @PathVariable Long productId) {
        return ResponseEntity.ok(orderService.getOrdersByProductId(productId, pageable));
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<Page<Order>> getOrdersByCustomerName(@RequestParam(required = false) Pageable pageable,
                                                               @PathVariable String customerName) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerName(customerName, pageable));
    }

}
