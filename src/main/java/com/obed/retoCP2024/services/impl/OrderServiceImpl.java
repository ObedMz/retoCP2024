package com.obed.retoCP2024.services.impl;

import com.obed.retoCP2024.entities.Order;
import com.obed.retoCP2024.entities.Product;
import com.obed.retoCP2024.exceptions.ResourceNotFoundException;
import com.obed.retoCP2024.repository.OrderRepository;
import com.obed.retoCP2024.repository.ProductRepository;
import com.obed.retoCP2024.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private Validator validator;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order createOrder(@Valid Order order) {
        validateOrder(order);

        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        order.setProduct(product);
        return orderRepository.save(order);
    }
    private void validateOrder(Order order) {
        var violations = validator.validate(order);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, @Valid Order purchaseOrder) {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) return orderRepository.save(purchaseOrder);

        Order purchaseOrder1 = order.get();
        purchaseOrder1.setQuantity(purchaseOrder.getQuantity());
        purchaseOrder1.setProduct(purchaseOrder.getProduct());
        return orderRepository.save(purchaseOrder1);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)) throw new ResourceNotFoundException("Order not found.");
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(Pageable pageable) {
        if(pageable == null)
            pageable = PageRequest.of(0,20);

        return orderRepository.findAll(pageable);
    }
}
