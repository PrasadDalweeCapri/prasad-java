package com.rest.webservice.controller;

import com.rest.webservice.dto.UserRequest;
import com.rest.webservice.entity.UserDb;
import com.rest.webservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users-db")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserController {

    private final UserService usersDbService;

    @GetMapping("/all")
    public List<UserDb> getAllUsers() {
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


    @PutMapping("/{id}")
    public String updateUser2(@PathVariable Integer id, @Valid @RequestBody UserRequest user) {
        return usersDbService.updateUser(id, user);
    }
}
