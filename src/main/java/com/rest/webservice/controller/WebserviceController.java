package com.rest.webservice.controller;

import com.rest.webservice.entity.User;
import com.rest.webservice.service.UsersDAOService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class WebserviceController
{

//    @Autowired
    //dependency injection is required ...either @Autowired or manual dependency injection is required, constructor based..
    //dependency injection is the safest one for creating checking immutable dependencies at the time of initialization..
    //as it prevents NullPointerException.
    //refer: https://www.linkedin.com/pulse/constructor-injection-vs-field-kiran-kumar-matam?trk=articles_directory
    private final UsersDAOService usersDAOService;

    //constructor injection is used here for dependency injection of UsersDAOService..
    //..if multiple constructors are used @Autowired has to be used
    //constructor injection makes sure all required dependencies are loaded at initialization preventing..
    //..the NullPointerException error
    public WebserviceController(UsersDAOService usersDAOService)
    {
        this.usersDAOService =usersDAOService;
    }

    //get all users
    @GetMapping("/all")
    public List<User> getAllUsers()
    {
        return usersDAOService.getAllUsers();
    }

    //get user by id
    @GetMapping("/{id}")
    public User getByID(@PathVariable @NotNull int id)
    {
        return usersDAOService.findUserByID(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user)
    {
        usersDAOService.addUser(user);
        return ResponseEntity.status(201).build();
    }

    //put and patch mapping remaining

    @DeleteMapping("/{id}")
    public String delete_user(@PathVariable @NotNull Integer id) {
        return usersDAOService.deleteUserByID(id);
    }

}
