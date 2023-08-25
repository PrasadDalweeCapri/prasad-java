package com.rest.webservice.service;

import com.rest.webservice.dto.UserRequest;
import com.rest.webservice.entity.UserDb;
import com.rest.webservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository usersDbRepository;

    public List<UserDb> getAllUsers() {
        try {
            return usersDbRepository.findAll();
        } catch (Throwable t) {
            log.error("Error occurred while retrieving all users, error:{}", t.getMessage());
            throw new RuntimeException("Error occurred.");
        }
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
            log.error("user doesn't exist with id:{}, error:{}",id, e.getMessage());
            throw new NoSuchElementException(new StringBuilder("Invalid id:").append(id.toString()).toString());
        } catch (Throwable t) {
            log.error("Error Occurred while finding user with id: {}, error:{}", id, t.getMessage());
            throw new RuntimeException("Error occurred.");
        }

        return user;
    }

    public String addNewUser(UserDb user) {
        try {
            usersDbRepository.save(user);
            log.info("New user added with id:{}",user.getId());
        } catch (Throwable t) {
            log.error("Error occurred while saving user details with data:{}, error:{}",user, t.getMessage());
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
            log.info("User with id:{} Deleted", id);
        } catch (NoSuchElementException e) {
            log.error("id doesn't exist, error:{}", e.getMessage());
            throw new NoSuchElementException(new StringBuilder("Invalid id:").append(id.toString()).toString());
        } catch (Throwable t) {
            log.error("Error occurred while deleting user, error:{}", t.getMessage());
            throw new RuntimeException("Error Occurred");
        }

        return "User Deleted.";
    }

    public String updateUser(Integer id, UserRequest user) {
        try {
            Optional<UserDb> userOptional = Optional.ofNullable(usersDbRepository.findById(id).orElseThrow(NoSuchElementException::new));
                UserDb userDb = userOptional.get();
                userDb.setName(user.name());
                userDb.setDate(user.date());
                usersDbRepository.save(userDb);
                log.info("User details updated with user id:{}.",id);
        } catch (NoSuchElementException e) {
            log.error("user couldn't be updated since user with id:{} doesn't exist, error:{}",id, e.getMessage());
            throw new NoSuchElementException(new StringBuilder("Invalid id:").append(id.toString()).toString());
        } catch (Throwable t) {
            log.error("Error while updating user details for user with id:{}, error:{}",id, t.getMessage());
            throw new RuntimeException("Error Occurred.");
        }
        return "Details Updated.";
    }
}
