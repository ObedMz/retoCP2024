package com.obed.retoCP2024.controllers;

import com.obed.retoCP2024.entities.PurchaseOrder;
import com.obed.retoCP2024.services.OrderService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<Page<PurchaseOrder>> getAll(Pageable pageable){
        return ResponseEntity.ok(orderService.getAllPurchaseOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getPurchaseOrderById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PurchaseOrder> createPurchase(@RequestBody PurchaseOrder purchaseOrder){
        return ResponseEntity.ok(orderService.createPurchaseOrder(purchaseOrder));
    }
    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id){
        orderService.deletePurchaseOrder(id);
    }

}
