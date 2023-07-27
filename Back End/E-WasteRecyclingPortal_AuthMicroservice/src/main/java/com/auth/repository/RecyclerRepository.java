package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.model.Recycler;

@Repository
public interface RecyclerRepository extends JpaRepository<Recycler, Integer> {

		Recycler findByEmail(String email);
}
