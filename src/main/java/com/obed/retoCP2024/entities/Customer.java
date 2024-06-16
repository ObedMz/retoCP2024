package com.obed.retoCP2024.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Embeddable
public class Customer {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Direction is required")
    private String direction;
}
