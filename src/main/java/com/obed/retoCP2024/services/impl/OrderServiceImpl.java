package com.obed.retoCP2024.services.impl;

import com.obed.retoCP2024.entities.PurchaseOrder;
import com.obed.retoCP2024.repositories.PurchaseOrderRepository;
import com.obed.retoCP2024.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return orderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder) {
        Optional<PurchaseOrder> order = orderRepository.findById(id);
        if(order.isPresent()){
            PurchaseOrder purchaseOrder1 = order.get();
            purchaseOrder1.setCostumer(purchaseOrder.getCostumer());
            purchaseOrder1.setQuantity(purchaseOrder.getQuantity());
            purchaseOrder1.setProduct_id(purchaseOrder.getProduct_id());
            orderRepository.save(purchaseOrder1);
        }
        return orderRepository.save(purchaseOrder);
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Page<PurchaseOrder> getAllPurchaseOrders(Pageable pageable) {
        if(pageable == null)
            pageable = PageRequest.of(0,20);

        return orderRepository.findAll(pageable);
    }
}
