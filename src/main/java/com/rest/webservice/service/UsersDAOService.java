package com.rest.webservice.service;

import com.rest.webservice.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsersDAOService {

    //making a param static means that the value of param will be shared across all instances of the class
    private static List<User> users=new ArrayList<>();
    private static Integer id=0;

    //static block is a block that executes first time the class is loaded in memory
    static {
        users.add(new User(++id,"Adam", LocalDate.of(2000,12,2)));
        users.add(new User(++id,"Badam",LocalDate.of(2001,8,12)));
        users.add(new User(++id,"Chadam",LocalDate.of(2002,1,11)));
        users.add(new User(++id, "Dadam", LocalDate.parse("2000-02-21")));
    }

    public List<User> getAllUsers()
    {
        return users;
    }

    public User findUserByID(Integer id)
    {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }

    public User addUser(User user)
    {
        user.setId(++id);
        users.add(user);
        return user;
    }

    public String deleteUserByID(Integer id) {
        Optional<User> targetUserOptional=users.stream().filter(user->user.getId()==id).findFirst();
        users.remove(targetUserOptional.get());
        return "User Removed";
    }
}
