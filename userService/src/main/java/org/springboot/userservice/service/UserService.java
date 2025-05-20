package org.springboot.userservice.service;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.exceptions.UserAlreadyExistsException;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springboot.userservice.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public Optional<Users> getUserByName(String username) {
        return userRepo.findByUsername(username);
    }
    public Users getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
    public Users addUser(Users newUser) {
        if (userRepo.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken: " + newUser.getUsername());
        }
        return userRepo.save(newUser);
    }

    public void deleteUser(int id) {
        Users user = getUserById(id);
        userRepo.delete(user);
    }
    public Users updateUser(int id, Users updatedUser) {
        Users existingUser = getUserById(id);
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        return userRepo.save(existingUser);
    }
}
