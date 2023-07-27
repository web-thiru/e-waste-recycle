package com.recyclerMicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recyclerMicroservice.model.Recycler;

@Repository
public interface RecyclerRepository extends JpaRepository<Recycler, Integer>{
	

}
