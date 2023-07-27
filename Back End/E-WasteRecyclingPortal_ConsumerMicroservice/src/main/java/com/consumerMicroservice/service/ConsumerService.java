package com.consumerMicroservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumerMicroservice.exception.ConsumerNotFoundException;
import com.consumerMicroservice.exception.NoContentFoundException;
import com.consumerMicroservice.model.Consumer;
import com.consumerMicroservice.repository.ConsumerRepository;

@Service
public class ConsumerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private ConsumerRepository consumerRepository;

	public String test() {
		LOGGER.info("Greeting method called");
		return "HI";
	}

	public Consumer registerConsumer(Consumer consumer) {
		LOGGER.info("Saving consumer: {}", consumer);
		return consumerRepository.save(consumer);
	}

	public void deleteConsumer(int id) throws ConsumerNotFoundException {
		LOGGER.info("Deleting consumer by ID: {}", id);
		if (!consumerRepository.existsById(id)) {
			LOGGER.warn("Consumer not found with ID: {}", id);
			throw new ConsumerNotFoundException(
					"Consumer not found with ID: " + id + ". so, delete opretion can't be done.");
		}
		consumerRepository.deleteById(id);
		LOGGER.info("Consumer deleted successfully");
	}

	public List<Consumer> getAllConsumers() throws NoContentFoundException {
		LOGGER.info("Getting all consumers");
		List<Consumer> consumers = consumerRepository.findAll();
		if (consumers.isEmpty()) {
			LOGGER.warn("No consumers found");
			throw new NoContentFoundException("No consumers found. Table is empty.");
		}
		LOGGER.info("Found {} consumers", consumers.size());
		return consumers;
	}

	public Consumer getConsumerById(int id) throws ConsumerNotFoundException {
		LOGGER.info("Getting consumer by ID: {}", id);
		return consumerRepository.findById(id)
				.orElseThrow(() -> new ConsumerNotFoundException("Consumer not found with id: " + id));

	}

}