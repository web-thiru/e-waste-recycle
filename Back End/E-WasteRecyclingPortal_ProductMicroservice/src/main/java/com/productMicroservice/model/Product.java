package com.productMicroservice.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column
    @NotBlank(message = "Name is required")
    @Size(max = 20, message = "Name cannot exceed 20 characters")
    private String name;

    @Column
    @NotBlank(message = "Category is required")
    private String category;

    @Column
    @NotBlank(message = "Brand name is required")
    @Size(max = 20, message = "Brand cannot exceed 20 characters")
    private String brand;

    @Column
    @NotBlank(message = "Accessories is required")
    private String accessories;

    @Column
    @Min(value = 1900, message = "Manufactured year must be a in the range 1900 - 2023")
    @Max(value = 2023, message = "Manufactured year must be a in the range 1900 - 2023")
    private int manufacturedYear;
   
    
   
    @Column
    private Long consumerId;

    @Column
    private Long recyclerId;
    
    @Column
    private boolean status;
    
}
