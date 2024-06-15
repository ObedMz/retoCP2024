package com.obed.retoCP2024.entities;

import lombok.Data;
import org.hibernate.annotations.Entity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Order {

    private String id;
    private String product_id;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "customer_id", nullable = false)
    private Costumer costumer;
}
