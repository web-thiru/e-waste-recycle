package com.consumerMicroserviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.consumerMicroservice.exception.ConsumerNotFoundException;
import com.consumerMicroservice.exception.NoContentFoundException;
import com.consumerMicroservice.model.Address;
import com.consumerMicroservice.model.Consumer;
import com.consumerMicroservice.repository.ConsumerRepository;
import com.consumerMicroservice.service.ConsumerService;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceTest {

	@Mock
	private ConsumerRepository consumerRepository;

	@InjectMocks
	private ConsumerService consumerService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegisterConsumer() {
		Consumer consumer = createConsumer();
		createAddress();

		when(consumerRepository.save(any(Consumer.class))).thenReturn(consumer);

		Consumer savedConsumer = consumerService.registerConsumer(consumer);

		assertNotNull(savedConsumer);
		assertEquals(consumer.getUsername(), savedConsumer.getUsername());
		assertEquals(consumer.getEmail(), savedConsumer.getEmail());
		assertEquals(consumer.getPhoneNumber(), savedConsumer.getPhoneNumber());
		assertEquals(consumer.getAddress(), savedConsumer.getAddress());
	}

	@Test
	public void testDeleteConsumer() throws ConsumerNotFoundException {
		int consumerId = 1;

		when(consumerRepository.existsById(consumerId)).thenReturn(true);
		doNothing().when(consumerRepository).deleteById(consumerId);

		consumerService.deleteConsumer(consumerId);

		verify(consumerRepository).deleteById(consumerId);
	}

	@Test
	public void testDeleteConsumer_ThrowsConsumerNotFoundException() {
		int consumerId = 1;

		when(consumerRepository.existsById(consumerId)).thenReturn(false);

		assertThrows(ConsumerNotFoundException.class, () -> consumerService.deleteConsumer(consumerId));
	}

	@Test
	public void testGetAllConsumers() throws NoContentFoundException {
		List<Consumer> consumers = new ArrayList<>();
		consumers.add(createConsumer());
		consumers.add(createConsumer());

		when(consumerRepository.findAll()).thenReturn(consumers);

		List<Consumer> result = consumerService.getAllConsumers();

		assertFalse(result.isEmpty());
		assertEquals(consumers.size(), result.size());
		assertEquals(consumers.get(0), result.get(0));
		assertEquals(consumers.get(1), result.get(1));
	}

	@Test
	public void testGetAllConsumers_ThrowsNoContentFoundException() {
		when(consumerRepository.findAll()).thenReturn(new ArrayList<>());

		assertThrows(NoContentFoundException.class, () -> consumerService.getAllConsumers());
	}

	@Test
	public void testGetConsumerById() throws ConsumerNotFoundException {
		int consumerId = 1;
		Consumer consumer = createConsumer();

		when(consumerRepository.findById(consumerId)).thenReturn(Optional.of(consumer));

		Consumer result = consumerService.getConsumerById(consumerId);

		assertNotNull(result);
		assertEquals(consumer, result);
	}

	@Test
	public void testGetConsumerById_ThrowsConsumerNotFoundException() {
		int consumerId = 1;

		when(consumerRepository.findById(consumerId)).thenReturn(Optional.empty());

		assertThrows(ConsumerNotFoundException.class, () -> consumerService.getConsumerById(consumerId));
	}

	private Consumer createConsumer() {
		Consumer consumer = new Consumer();
		consumer.setConsumerId(1);
		consumer.setUsername("Thiyagu");
		consumer.setEmail("thiyagu@example.com");
		consumer.setPhoneNumber(1234567890L);
		consumer.setAddress(createAddress());

		return consumer;
	}

	private Address createAddress() {
		Address address = new Address();
		address.setAddressId(1);
		address.setStreet("123 Main Street");
		address.setCity("Chennai");
		address.setState("Tamil nadu");
		address.setCountry("India");
		address.setZipCode(600061);

		return address;
	}
}
