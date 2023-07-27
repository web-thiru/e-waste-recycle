package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
//	Roles findById(int id);

}
