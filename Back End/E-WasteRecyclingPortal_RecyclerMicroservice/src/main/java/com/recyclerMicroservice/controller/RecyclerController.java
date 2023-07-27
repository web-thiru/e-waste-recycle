package com.recyclerMicroservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recyclerMicroservice.dto.ProductDto;
import com.recyclerMicroservice.exception.NoContentFoundException;
import com.recyclerMicroservice.exception.RecyclerNotFoundException;
import com.recyclerMicroservice.fiegn.ProductFeignClientInterface;
import com.recyclerMicroservice.model.Recycler;
import com.recyclerMicroservice.service.ListingProducts;
import com.recyclerMicroservice.service.RecyclerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recycler")
//@CrossOrigin(origins = "http://localhost:3000")
public class RecyclerController {

	private final RecyclerService recyclerService;
	private final ProductFeignClientInterface productFeignClient;
	private final ListingProducts listingProducts;

	private static final Logger LOGGER = LoggerFactory.getLogger(RecyclerController.class);

	
	@Autowired
	public RecyclerController(RecyclerService recyclerService, ProductFeignClientInterface productFeignClient,
			ListingProducts listingProducts, @Value("${server.port}") String port) {
		this.recyclerService = recyclerService;
		this.productFeignClient = productFeignClient;
		this.listingProducts = listingProducts;
	}

	@PostMapping("/registerRecycler")
	public ResponseEntity<Recycler> createRecycler(@Valid @RequestBody Recycler recycler) {
		Recycler savedRecycler = recyclerService.saveRecycler(recycler);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRecycler);
	}

	@GetMapping("/viewRecyclerDetails/{id}")
	public ResponseEntity<Recycler> ViewRecyclerDetails(@PathVariable int id) throws RecyclerNotFoundException {
		Recycler recycler = recyclerService.getRecyclerById(id);
		return ResponseEntity.ok(recycler);
	}

	@PutMapping("/updateRecyclerDetail/{id}")
	public ResponseEntity<Recycler> updateRecyclerDetails(@PathVariable int id, @Valid @RequestBody Recycler recycler)
			throws RecyclerNotFoundException {
		recycler.setRecyclerId(id);
		Recycler updatedRecycler = recyclerService.saveRecycler(recycler);
		return ResponseEntity.ok(updatedRecycler);
	}

	@DeleteMapping("/deleteRecycler/{id}")
	public ResponseEntity<Void> deleteRecycler(@PathVariable int id) throws RecyclerNotFoundException {
		recyclerService.deleteRecycler(id);
		return ResponseEntity.noContent().build();
	}
	


	/*------------------------------ Additional Functions ----------------------*/

	
	@GetMapping("/getAllRecyclers")
	public ResponseEntity<List<Recycler>> getAllRecyclers() throws NoContentFoundException {
		List<Recycler> recyclers = recyclerService.getAllRecyclers();
		return ResponseEntity.ok(recyclers);
	}

	/*---------------------- From Product MicroService ------------------*/

	@GetMapping("products/myProducts/{id}")
	public ResponseEntity<List<ProductDto>> getMyProducts(@PathVariable int id) {
		return ResponseEntity.ok(listingProducts.listingMyProducts(id));
	}

	@GetMapping("products/notPickedUpProducts")
	public ResponseEntity<List<ProductDto>> getNotPickedUpProducts() {
		return ResponseEntity.ok(listingProducts.listNotPickedUpProductsByCategoryCount());
	}

	@GetMapping("products/viewProductDetails/{id}")
	public ResponseEntity<ProductDto> viewProductDetails(@PathVariable int id) {
		return ResponseEntity.ok(productFeignClient.viewProductDetails(id));
	}

	@GetMapping("/products/getAllProducts")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> products = productFeignClient.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@PutMapping("/pickUpProduct/{id}")
	public ResponseEntity<String> pickUpProduct(@PathVariable int id, @RequestBody ProductDto product  ){
		productFeignClient.updateProduct(id, product);
		LOGGER.info("product", product);
		return ResponseEntity.ok("Successfully picked Up");
	}

}
