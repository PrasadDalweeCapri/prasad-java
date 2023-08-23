package com.rest.webservice.controller;

import com.rest.webservice.entity.User;
import com.rest.webservice.service.UsersDAOService;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @GetMapping("/get_all")
    public List<User> getAllUsers()
    {
        return usersDAOService.getAllUsers();
    }

    @GetMapping("/get_by_id")
    public User getByID(@RequestParam int id)
    {
        return usersDAOService.findUserByID(id);
    }

    @PostMapping("/add_user")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user)
    {
        usersDAOService.addUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/delete_by_id")
    public String delete_user(@RequestParam Integer id) {
        return usersDAOService.deleteUserByID(id);
    }

}
