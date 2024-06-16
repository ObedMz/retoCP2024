package com.obed.retoCP2024.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Embeddable
public class Customer {
    private String name;
    private String lastName;
    private String direction;
}
