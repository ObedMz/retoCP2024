package com.obed.retoCP2024.repository;

import com.obed.retoCP2024.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByProductId(Long productId, Pageable pageable);

    Page<Order> findByCustomerName(String customerName, Pageable pageable);
}
