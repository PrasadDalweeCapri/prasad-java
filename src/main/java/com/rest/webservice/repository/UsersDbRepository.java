package com.rest.webservice.repository;

import com.rest.webservice.entity.UserDb;
import org.springframework.data.repository.CrudRepository;

public interface UsersDbRepository extends CrudRepository<UserDb, Integer>
{
}
