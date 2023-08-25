package com.rest.webservice.controller;

import com.rest.webservice.entity.UserDb;
import com.rest.webservice.service.UsersDbService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping("/users-db")
public class UsersDbController {

    @Autowired
    private UsersDbService usersDbService;

    @GetMapping("/all")
    public ArrayList<UserDb> getAllUsers() {
        return usersDbService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDb getUserById(@PathVariable Integer id) {
        return usersDbService.getById(id);
    }

    @PostMapping("/")
    public String addNewUser(@RequestBody UserDb user) {
        return usersDbService.addNewUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return usersDbService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody UserDb user) {
        return usersDbService.updateUser(id, user);
    }
}
