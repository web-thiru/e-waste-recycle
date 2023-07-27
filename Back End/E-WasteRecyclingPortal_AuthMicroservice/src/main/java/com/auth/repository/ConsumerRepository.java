package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.model.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
	Consumer findByEmail(String email);
}
