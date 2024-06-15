package com.obed.retoCP2024.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;
    private String direction;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

}
