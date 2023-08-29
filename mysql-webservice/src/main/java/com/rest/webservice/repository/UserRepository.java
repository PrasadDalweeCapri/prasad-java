package com.rest.webservice.repository;

import com.rest.webservice.entity.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserDb, Integer> {
}
