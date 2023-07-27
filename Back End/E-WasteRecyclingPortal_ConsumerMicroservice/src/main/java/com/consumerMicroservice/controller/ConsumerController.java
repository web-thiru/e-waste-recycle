package com.consumerMicroservice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumerMicroservice.dto.ProductDto;
import com.consumerMicroservice.exception.ConsumerNotFoundException;
import com.consumerMicroservice.exception.NoContentFoundException;
import com.consumerMicroservice.feign.ProductFeignClientInterface;
import com.consumerMicroservice.model.Consumer;
import com.consumerMicroservice.service.ConsumerService;
import com.consumerMicroservice.service.ListingProducts;

import jakarta.ws.rs.BadRequestException;

@RestController
@RequestMapping("/consumer")
//@CrossOrigin(origins = "http://localhost:3000")
public class ConsumerController {

	private final ConsumerService consumerService;
	private final ProductFeignClientInterface productFeignClient;
	private final ListingProducts listingProducts;

	@Autowired
	public ConsumerController(ConsumerService consumerService, ProductFeignClientInterface productFeignClient,
			ListingProducts listingProducts, @Value("${server.port}") String port) {
		this.consumerService = consumerService;
		this.productFeignClient = productFeignClient;
		this.listingProducts = listingProducts;
	}

//	@PostMapping("/registerConsumer")
//	public ResponseEntity<Consumer> registerConsumer(@Valid @RequestBody Consumer consumer) {
//		Consumer createdConsumer = consumerService.registerConsumer(consumer);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdConsumer);
//	}

	@GetMapping("/viewConsumerDetails/{id}")
	public ResponseEntity<Consumer> viewConsumerDetails(@PathVariable int id) throws ConsumerNotFoundException {
		Consumer consumer = consumerService.getConsumerById(id);
		return ResponseEntity.ok(consumer);
	}

	@PutMapping("/updateConsumerDetails/{id}")
	public ResponseEntity<Consumer> updateConsumerDetails(@PathVariable int id, @RequestBody Consumer consumer) {
		consumer.setConsumerId(id);
		Consumer updatedConsumer = consumerService.registerConsumer(consumer);
		return ResponseEntity.ok(updatedConsumer);
	}

	@DeleteMapping("/deleteConsumer/{id}")
	public String deleteConsumer(@PathVariable int id) throws ConsumerNotFoundException {
		consumerService.deleteConsumer(id);
		return "Successfully Deleted!!";
	}

	/*------- Additional Functions -------------------------*/

	@GetMapping("/getAllConsumers")
	public ResponseEntity<List<Consumer>> getAllConsumers() throws NoContentFoundException {
		List<Consumer> consumers = consumerService.getAllConsumers();
		System.out.println(consumers);
		return new ResponseEntity<List<Consumer>>(consumers, HttpStatus.CREATED);
	}

	/*---------------------------------- From Product MicroService  ------------------------------------------*/

	@PostMapping("/products/addProduct")
	public ResponseEntity<ProductDto> addProduct(@RequestBody @Validated ProductDto productDto)
			throws BadRequestException {

		ProductDto createdProduct = productFeignClient.addProduct(productDto);
		createdProduct.setModifiedDate(new Date());
		createdProduct.setStatus(false);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@GetMapping("/products/myProducts/{id}")
	public ResponseEntity<List<ProductDto>> myProducts(@PathVariable int id) {
		List<ProductDto> myProducts = listingProducts.myProducts(id);
		return ResponseEntity.ok(myProducts);
	}

	@GetMapping("/products/viewProductDetails/{id}")
	public ResponseEntity<ProductDto> viewProductDetails(@PathVariable int id) {
		ProductDto product = productFeignClient.viewProductDetails(id);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/products/updateProduct/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable int id, @RequestBody ProductDto ProductDto) {
		ProductDto updatedProduct = productFeignClient.updateProduct(id, ProductDto);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/products/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id) {
		return productFeignClient.deleteProduct(id);
	}
	
	@GetMapping("/products/pickedUpProducts/{id}")
	public ResponseEntity<List<ProductDto>> pickedUpProducts(@PathVariable int id) {
		List<ProductDto> myProducts = listingProducts.pickedUpProducts(id);
		return ResponseEntity.ok(myProducts);
	}
	/*------- Additional Functions -------------------------*/

	@GetMapping("/products/getAllProducts")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> allProducts = productFeignClient.getAllProducts();
		return ResponseEntity.ok(allProducts);
	}

}
