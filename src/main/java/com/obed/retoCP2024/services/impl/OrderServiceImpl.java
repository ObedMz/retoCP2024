package com.obed.retoCP2024.services.impl;

import com.obed.retoCP2024.entities.Order;
import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.exceptions.mappers.ResourceNotFoundException;
import com.obed.retoCP2024.repository.OrderRepository;
import com.obed.retoCP2024.repository.ProductRepository;
import com.obed.retoCP2024.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing orders.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Creates a new order.
     *
     * @param order the order to be created
     * @return the created order
     * @throws ResourceNotFoundException if the associated product is not found
     */
    @Override
    @Transactional
    public Order createOrder(Order order) {
        logger.info("Creating order for product ID: {}", order.getProduct().getId());

        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        order.setProduct(product);
        Order savedOrder = orderRepository.save(order);
        logger.info("Order created with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    /**
     * Updates an existing order.
     *
     * @param id            the ID of the order to update
     * @param purchaseOrder the updated order details
     * @return the updated order
     */
    @Override
    @Transactional
    public Order updateOrder(Long id, Order purchaseOrder) {
        logger.info("Updating order with ID: {}", id);
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) {
            logger.warn("Order with ID: {} not found, creating new order", id);
            return orderRepository.save(purchaseOrder);
        }

        Order purchaseOrder1 = order.get();
        purchaseOrder1.setQuantity(purchaseOrder.getQuantity());
        purchaseOrder1.setProduct(purchaseOrder.getProduct());
        Order updatedOrder = orderRepository.save(purchaseOrder1);
        logger.info("Order with ID: {} updated", id);
        return updatedOrder;
    }

    /**
     * Deletes an order by ID.
     *
     * @param id the ID of the order to delete
     * @throws ResourceNotFoundException if the order with the given ID is not found
     */
    @Override
    @Transactional
    public void deleteOrder(Long id) {
        logger.info("Deleting order with ID: {}", id);
        if (!orderRepository.existsById(id)) {
            logger.error("Order with ID: {} not found, cannot delete", id);
            throw new ResourceNotFoundException("Order not found.");
        }
        orderRepository.deleteById(id);
        logger.info("Order with ID: {} deleted", id);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return the retrieved order
     * @throws ResourceNotFoundException if the order with the given ID is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        logger.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves all orders with pagination.
     *
     * @param pageable pagination information
     * @return a page of orders
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(Pageable pageable) {
        if(pageable == null) pageable = PageRequest.of(0,20);

        return orderRepository.findAll(pageable);
    }

    /**
     * Retrieves orders by customer name with pagination.
     *
     * @param customerName the name of the customer
     * @param pageable     pagination information
     * @return a page of orders filtered by customer name
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrdersByCustomerName(String customerName,Pageable pageable) {
        logger.info("Fetching orders for customer: {}", customerName);
        if(pageable == null) pageable = PageRequest.of(0,20);
        return orderRepository.findByCustomerName(customerName,pageable);
    }

    /**
     * Retrieves orders by product ID with pagination.
     *
     * @param productId the ID of the product
     * @param pageable  pagination information
     * @return a page of orders filtered by product ID
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrdersByProductId(Long productId,Pageable pageable) {
        logger.info("Fetching orders for product ID: {}", productId);
        if(pageable == null) pageable = PageRequest.of(0,20);
        return orderRepository.findByProductId(productId,pageable);
    }
}
