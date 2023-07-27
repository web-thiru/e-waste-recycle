package com.recyclerMicroservice.fiegn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.recyclerMicroservice.dto.ProductDto;

import jakarta.validation.Valid;

@FeignClient(name = "PRODUCT-MICROSERVICE", path = "/products")
public interface ProductFeignClientInterface {

	@GetMapping("/viewAllProducts")
	public List<ProductDto> getAllProducts() ;

	@GetMapping("/viewProductDetails/{id}")
	public ProductDto viewProductDetails(@PathVariable int id);
	
	@PutMapping("/updateProduct/{id}")
	public ProductDto updateProduct(@PathVariable int id, @Valid @RequestBody ProductDto product);
}
