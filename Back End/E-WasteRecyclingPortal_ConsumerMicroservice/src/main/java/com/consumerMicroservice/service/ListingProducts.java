package com.consumerMicroservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consumerMicroservice.dto.ProductDto;
import com.consumerMicroservice.feign.ProductFeignClientInterface;

@Component
public class ListingProducts {

	@Autowired
	private ProductFeignClientInterface productFeignClient;

	public List<ProductDto> myProducts(int id) {
		List<ProductDto> allProducts = productFeignClient.getAllProducts();
		List<ProductDto> myProducts = new ArrayList<ProductDto>();
		for (ProductDto product : allProducts) {
			if (product.getConsumerId() == id) {
				myProducts.add(product);
			}
		}
		return myProducts;
	}

	public List<ProductDto> pickedUpProducts(int id) {
		List<ProductDto> allProducts = productFeignClient.getAllProducts();
		List<ProductDto> pickedUpProducts = new ArrayList<ProductDto>();

		for (ProductDto product : allProducts) {
			if (product.isStatus() && product.getConsumerId() == id) {
				pickedUpProducts.add(product);
			}
		}
		return pickedUpProducts;
	}

}
