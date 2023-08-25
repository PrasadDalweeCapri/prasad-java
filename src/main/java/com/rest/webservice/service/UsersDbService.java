package com.rest.webservice.service;

import com.rest.webservice.entity.UserDb;
import com.rest.webservice.repository.UsersDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UsersDbService {

    @Autowired
    private UsersDbRepository usersDbRepository;

    public ArrayList<UserDb> getAllUsers() {
        ArrayList<UserDb> users = new ArrayList<>();
        try {
            usersDbRepository.findAll().forEach(users::add);
        } catch (Throwable t) {
            log.error("Error occurred while retrieving all users, error:{}", t.getMessage());
            throw new RuntimeException();
        }
        return users;
    }

    public UserDb getById(Integer id) {
        UserDb user = null;
        try {
            Optional<UserDb> userOptional = usersDbRepository.findById(id);
            if (userOptional.isPresent()) {
                user = userOptional.get();
            } else {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            log.error("id doesn't exist, error:{}", e.getMessage());
            throw new NoSuchElementException("Invalid id.");
        } catch (Throwable t) {
            log.error("Error Occurred while finding user with id: {}, error:{}", id, t.getMessage());
            throw new RuntimeException("Error occurred.");
        }

        return user;
    }

    public String addNewUser(UserDb user) {
        try {
            usersDbRepository.save(user);
            log.info("New User Added.");
        } catch (Throwable t) {
            log.error("Error occurred while saving user details, error:{}", t.getMessage());
            throw new RuntimeException("Error Occurred");
        }
        return "User added.";
    }

    public String deleteUser(Integer id) {
        try {
            if (usersDbRepository.findById(id).isEmpty()) {
                throw new NoSuchElementException();
            }
            usersDbRepository.deleteById(id);
            log.info("User with id={} Deleted", id);
        } catch (NoSuchElementException e) {
            log.error("id doesn't exist, error:{}", e.getMessage());
            throw new NoSuchElementException("Invalid id.");
        } catch (Throwable t) {
            log.error("Error occurred while deleting user, error:{}", t.getMessage());
            throw new RuntimeException("Error Occurred");
        }

        return "User Deleted.";
    }

    public String updateUser(Integer id, UserDb userDb) {
        try {
            Optional<UserDb> userOptional = usersDbRepository.findById(id);
            if (userOptional.isPresent()) {
                UserDb user = userOptional.get();
                if(!userDb.getName().isBlank()) {
                    user.setName(userDb.getName());
                }
                if(userDb.getDate()!=null)
                {user.setDate(userDb.getDate());}
                usersDbRepository.save(user);
                log.info("User details updated.");
            } else {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            log.error("id doesn't exist, error:{}", e.getMessage());
            throw new NoSuchElementException("Invalid id.");
        } catch (Throwable t) {
            log.error("Error while updating user details, error:{}", t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return "Details Updated.";
    }
}
