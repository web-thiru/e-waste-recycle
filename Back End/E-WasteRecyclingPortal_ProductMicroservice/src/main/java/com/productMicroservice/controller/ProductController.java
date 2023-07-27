package com.productMicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productMicroservice.exception.NoContentFoundException;
import com.productMicroservice.exception.ProductNotFoundException;
import com.productMicroservice.model.Product;
import com.productMicroservice.service.ProductService;

import jakarta.validation.Valid;

@RestController

@RequestMapping("/products")
//@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Value("${server.port}")
	private String port;

	@PostMapping("/addProduct")
	public Product addProduct(@Valid @RequestBody Product product) {
		Product addedProduct = productService.saveProduct(product);
		return addedProduct;
	}

	@GetMapping("/viewProductDetails/{id}")
	public Product viewProductDetails(@PathVariable Long id) throws ProductNotFoundException {
		Product product = productService.getProductById(id);
		return product;
	}

	@PutMapping("/updateProduct/{id}")
	public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
		product.setProductId(id);
		Product updatedProduct = productService.saveProduct(product);
		return updatedProduct;
	}
	
	@PutMapping("/pickUpProduct/{id}")
	public Product pickUpProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
		product.setProductId(id);
		product.setStatus(true);
		Product updatedProduct = productService.saveProduct(product);
		return updatedProduct;
	}

	@DeleteMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
		productService.deleteProduct(id);
		return "Successfully Deleted";
	}

	/*-------------------- Additional Functionalities -----------------*/
	
	@GetMapping("/test")
	public String test() {
		return "Welcome to 'Product Microservice'!!. It is running in : " + port;
	}

	@GetMapping("/viewAllProducts")
	public List<Product> getAllProducts() throws NoContentFoundException {
		List<Product> products = productService.getAllProducts();
		return products;
	}

}
