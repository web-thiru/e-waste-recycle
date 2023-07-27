package com.consumerMicroservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column()
	private int addressId;

	@Column(nullable = false)
	@NotBlank(message = "Street is required")
	private String street;

	@Column(nullable = false)
	@NotBlank(message = "City is required")
	private String city;

	@Column(nullable = false)
	@NotBlank(message = "State is required")
	private String state;

	@Column(nullable = false)
	@NotBlank(message = "Country is required")
	private String country;

	@Column(nullable = false)
	@DecimalMin(value = "100000", message = "ZipCode must be in 6 digits")
    @DecimalMax(value = "999999", message = "ZipCode year must be in 6 digits")
	private int zipCode;

	@OneToOne(mappedBy = "address")
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Consumer consumer;

}
