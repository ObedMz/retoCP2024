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

/**
 * Controller class for handling operations related to orders.
 */
@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Endpoint to create a new order.
     *
     * <p>Creates an order based on the provided request body and returns HTTP status 201 (Created) with
     * the created order in the response body.</p>
     *
     * @param order the order object to be created
     * @return ResponseEntity containing the created order and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

    /**
     * Endpoint to retrieve all orders with pagination support.
     *
     * <p>Returns HTTP status 200 (OK) with a page of orders in the response body. Supports optional
     * pagination parameters.</p>
     *
     * @param pageable pagination information (optional)
     * @return ResponseEntity containing a page of orders and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<Page<Order>> getAll(@RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    /**
     * Endpoint to retrieve an order by its ID.
     *
     * <p>Returns HTTP status 200 (OK) with the order matching the provided ID in the response body.</p>
     *
     * @param id the ID of the order to retrieve
     * @return ResponseEntity containing the order with HTTP status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * Endpoint to delete an order by its ID.
     *
     * <p>Returns HTTP status 204 (No Content) if the order is successfully deleted.</p>
     *
     * @param id the ID of the order to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to retrieve orders by a product ID with pagination support.
     *
     * <p>Returns HTTP status 200 (OK) with a page of orders associated with the specified product ID
     * in the response body. Supports optional pagination parameters.</p>
     *
     * @param pageable pagination information (optional)
     * @param productId the ID of the product to retrieve orders for
     * @return ResponseEntity containing a page of orders and HTTP status 200 (OK)
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<Order>> getOrdersByProductId(@RequestParam(required = false) Pageable pageable,
                                                            @PathVariable Long productId) {
        return ResponseEntity.ok(orderService.getOrdersByProductId(productId, pageable));
    }

    /**
     * Endpoint to retrieve orders by a customer name with pagination support.
     *
     * <p>Returns HTTP status 200 (OK) with a page of orders associated with the specified customer name
     * in the response body. Supports optional pagination parameters.</p>
     *
     * @param pageable pagination information (optional)
     * @param customerName the name of the customer to retrieve orders for
     * @return ResponseEntity containing a page of orders and HTTP status 200 (OK)
     */
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<Page<Order>> getOrdersByCustomerName(@RequestParam(required = false) Pageable pageable,
                                                               @PathVariable String customerName) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerName(customerName, pageable));
    }

}
