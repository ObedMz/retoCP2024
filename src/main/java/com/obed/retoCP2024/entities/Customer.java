package com.obed.retoCP2024.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String direction;
}
