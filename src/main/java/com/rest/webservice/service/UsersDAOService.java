package com.rest.webservice.service;

import com.rest.webservice.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class UsersDAOService {

    //making a param static means that the value of param will be shared across all instances of the class
    private final CopyOnWriteArrayList<User> users=new CopyOnWriteArrayList<>();
//    private final List<User> users=new ArrayList<>();

    //making id thread safe: 3 ways-> Synchronized, Volatile, AtomicInteger
    //refer: https://www.geeksforgeeks.org/difference-between-atomic-volatile-and-synchronized-in-java/
    private static AtomicInteger id=new AtomicInteger(0);

    public Integer getLastUserId(){return id.get();}

    public Integer getUserCount(){return users.size();}
    public List<User> getAllUsers()
    {
        return users;
    }

    public User findUserByID(Integer id)
    {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }


    public void addUser(User user)
    {
        user.setId(id.getAndIncrement());
        users.add(user);
    }

    public String deleteUserByID(Integer id) {
        Optional<User> targetUserOptional=users.stream().filter(user->user.getId()==id).findFirst();
        //isPresent() is not used because in case it isn't present above line throws an error which handled
        users.remove(targetUserOptional.get());
        return "User Removed";
    }

    public String updateUserByID(Integer id, User user)
    {
        users.get(id).setName(user.getName());
        users.get(id).setDate(user.getDate());
        return "User Updated";
    }
}
