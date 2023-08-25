package com.rest.webservice.service;

import com.rest.webservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Slf4j
public class UsersDAOService {

    //making a param static means that the value of param will be shared across all instances of the class
    private final CopyOnWriteArrayList<User> users=new CopyOnWriteArrayList<>();
//    private final List<User> users=new ArrayList<>();

    //making id thread safe: 3 ways-> Synchronized, Volatile, AtomicInteger
    //refer: https://www.geeksforgeeks.org/difference-between-atomic-volatile-and-synchronized-in-java/
    private static final AtomicInteger id=new AtomicInteger(0);

    public Integer getLastUserId(){return id.get();}

    public Integer getUserCount(){return users.size();}
    public List<User> getAllUsers()
    {
        return users;
    }

    public User findUserByID(Integer id)
    {
        User targetUser = null;
        try
        {
            targetUser= users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
        }
        catch (NoSuchElementException e)
        {
            log.error("The requested id:{} doesn't exist in users",id);
            throw new NoSuchElementException("No user found");
        }
        catch (Throwable t)
        {
            log.error("Error while finding user with id:{}",id);
        }
        return targetUser;
    }


    public void addUser(User user)
    {
        try
        {
            user.setId(id.getAndIncrement());
            users.add(user);
            log.info("New user added on id:{}",user.getId());
        }
        catch (Throwable t)
        {
            log.error("Error occurred while adding user:{}, and the error is: {}",user,t.getMessage());
        }
    }

    public String deleteUserByID(Integer id) {
        try{
            Optional<User> targetUserOptional = users.stream().filter(user -> user.getId() == id).findFirst();
            //isPresent() is not used because in case it isn't present above line throws an error which handled
            users.remove(targetUserOptional.get());
            log.info("User with id:{} removed successfully",id);
            return "User Removed";
        }
        catch (NoSuchElementException  e)
        {
            log.error("The requested id:{} doesn't exist, and the error is:{}",id,e.getMessage());
        }
        catch (Throwable t)
        {
            log.error("Error occurred while deleting user with id:{}",id);
        }
        return "Removal of user failed, provide correct data.";
    }

    public String updateUserByID(Integer id, User user)
    {
        try {
            users.get(id).setName(user.getName());
            users.get(id).setDate(user.getDate());
            log.info("User Updated Successfully: {}", users);
            return "User Updated";
        }
        catch (Throwable t)
        {
            log.error("error occurred when updating id:{}, and the error is: {}",user.getId(),t.getMessage());
        }
        return "Update Failed, input valid ID.";
    }
}
