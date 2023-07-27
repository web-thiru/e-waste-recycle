package com.recyclerMicroservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recyclerMicroservice.exception.NoContentFoundException;
import com.recyclerMicroservice.exception.RecyclerNotFoundException;
import com.recyclerMicroservice.model.Recycler;
import com.recyclerMicroservice.repository.RecyclerRepository;

@Service
public class RecyclerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecyclerService.class);

	private final RecyclerRepository recyclerRepository;

	@Autowired
	public RecyclerService(RecyclerRepository recyclerRepository) {
		this.recyclerRepository = recyclerRepository;
	}

	public List<Recycler> getAllRecyclers() throws NoContentFoundException {
		List<Recycler> recyclers = recyclerRepository.findAll();

		if (recyclers.isEmpty()) {
			LOGGER.warn("No Recycler details found. Table is empty.");
			throw new NoContentFoundException("No Recycler details found. Table is empty.");
		} else {
			LOGGER.info("Fetching all recyclers");
			return recyclers;
		}

	}

	public Recycler getRecyclerById(int id) throws RecyclerNotFoundException {
		LOGGER.info("Fetching recycler by ID: {}", id);
		Recycler recycler = recyclerRepository.findById(id)
				.orElseThrow(() -> new RecyclerNotFoundException("Recycler not found with ID: " + id));
		return recycler;
	}

	public Recycler saveRecycler(Recycler recycler) {
		LOGGER.info("Saving recycler: {}", recycler);
		Recycler savedRecycler = recyclerRepository.save(recycler);
		return savedRecycler;
	}

	public Void deleteRecycler(int id) throws RecyclerNotFoundException {
		LOGGER.info("Deleting recycler with ID: {}", id);
		if (recyclerRepository.existsById(id)) {
			recyclerRepository.deleteById(id);
			LOGGER.info("Recycler deleted successfully");
		} else {
			throw new RecyclerNotFoundException("Recycler not found with ID: " + id);
		}
		return null;
	}
	
	
}
