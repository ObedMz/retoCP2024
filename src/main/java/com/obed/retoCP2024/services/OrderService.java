package com.obed.retoCP2024.services;

import com.obed.retoCP2024.entities.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);

    PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder);

    void deletePurchaseOrder(Long id);

    PurchaseOrder getPurchaseOrderById(Long id);

    List<PurchaseOrder> getAllPurchaseOrders();

    Page<PurchaseOrder> getAllPurchaseOrders(Pageable pageable);
}
