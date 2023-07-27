package com.productMicroservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productMicroservice.exception.NoContentFoundException;
import com.productMicroservice.exception.ProductNotFoundException;
import com.productMicroservice.model.Product;
import com.productMicroservice.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() throws NoContentFoundException {
		List<Product> products = productRepository.findAll();
		if (products.isEmpty()) {
			LOGGER.warn("No products found");
			throw new NoContentFoundException("No product details found. Table is empty.");
		} else {
			LOGGER.info("Fetching all products");
			return products;
		}

	}

	public Product getProductById(Long id) throws ProductNotFoundException {
		LOGGER.info("Fetching product by ID: {}", id);
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
	}

	public Product saveProduct(Product product) {
		LOGGER.info("Saving product: {}", product);
		return productRepository.save(product);
	}

	public void deleteProduct(Long id) throws ProductNotFoundException {
		LOGGER.info("Deleting product with ID: {}", id);
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			LOGGER.info("Product deleted successfully");
		} else {
			throw new ProductNotFoundException(
					"Product not found with ID: " + id + ". So, can't do the delete operation.");
		}
	}
}
