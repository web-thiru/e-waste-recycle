package com.consumerMicroservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.consumerMicroservice.dto.ProductDto;

import jakarta.validation.Valid;

@FeignClient(name = "PRODUCT-MICROSERVICE")
public interface ProductFeignClientInterface {

	@GetMapping("products/test")
	public String test();

	@PostMapping("/products/addProduct")
	public ProductDto addProduct(@Valid @RequestBody ProductDto product);

	@GetMapping("/products/viewAllProducts")
	public List<ProductDto> getAllProducts();

	@GetMapping("/products/viewProductDetails/{id}")
	public ProductDto viewProductDetails(@PathVariable int id);

	@PutMapping("/products/updateProduct/{id}")
	public ProductDto updateProduct(@PathVariable int id, @Valid @RequestBody ProductDto product);
	
	@DeleteMapping("/products/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id);
}