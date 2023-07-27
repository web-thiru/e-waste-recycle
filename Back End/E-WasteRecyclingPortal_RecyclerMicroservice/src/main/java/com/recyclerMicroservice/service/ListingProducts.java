package com.recyclerMicroservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.recyclerMicroservice.dto.ProductDto;
import com.recyclerMicroservice.fiegn.ProductFeignClientInterface;

@Component
public class ListingProducts {

	private static final Logger logger = Logger.getLogger(ListingProducts.class.getName());

	@Autowired
	private ProductFeignClientInterface feignClient;

	public List<ProductDto> listingMyProducts(int id) {
		List<ProductDto> allProducts = feignClient.getAllProducts();
		List<ProductDto> pickedUpProducts = new ArrayList<>();
		int currentRecyclerId = id;

		for (ProductDto p : allProducts) {
			if (p.getRecyclerId() == currentRecyclerId) {
				pickedUpProducts.add(p);
			}
		}

		return pickedUpProducts;
	}

	public List<ProductDto> listNotPickedUpProductsByCategoryCount() {
		List<ProductDto> allProducts = feignClient.getAllProducts();
		List<ProductDto> notPickedUpProducts = new ArrayList<>();
		List<ProductDto> pickedUpProducts = new ArrayList<>();

		System.out.println(allProducts.toString());
		for (ProductDto p : allProducts) {
			if (p.getRecyclerId() == 0) {
				notPickedUpProducts.add(p);
			} else {
				pickedUpProducts.add(p);
			}
		}

		// Create a map to store the category-wise count
		Map<String, Integer> categoryCount = new HashMap<>();

		// Count the products by category
		for (ProductDto product : pickedUpProducts) {
			String category = product.getCategory();
			categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
		}

		// Sort the notPickedUpProducts list based on category count
		notPickedUpProducts.sort((p1, p2) -> {
			int count1 = categoryCount.getOrDefault(p1.getCategory(), 0);
			int count2 = categoryCount.getOrDefault(p2.getCategory(), 0);
			return Integer.compare(count2, count1);
		});

		// Log the category-wise count
		for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
			String category = entry.getKey();
			int count = entry.getValue();
			logger.log(Level.INFO, "Category: " + category + ", Count: " + count);
		}

		return notPickedUpProducts;
	}

}
