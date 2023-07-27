package com.consumerMicroservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consumer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int consumerId;

	@Column(nullable = false)
	@NotBlank(message = "Name is required")
	@Size(max = 25, message = "Name must be at most 25 characters")
	private String username;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@Column(unique = true)
	@Min(value = 1000000000L, message = "Phone number must be at least 10 digits")
	@Max(value = 9999999999L, message = "Phone number can have at most 10 digits")
	private Long phoneNumber;
	
	@Column
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	@Valid
	private Address address;

}
