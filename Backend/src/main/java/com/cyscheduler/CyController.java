package com.cyscheduler;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CyController {
    @Autowired
    private UserRepository userRepository;

    public CyController(UserRepository ur) {
        userRepository = ur;
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId) {
        return (User)userRepository.findByUuid(userId).toArray()[0];
    }

    @GetMapping("/users")
    public String getUsers() {
        return new Gson().toJson(userRepository.findAll());
    }

    @CrossOrigin
    @PostMapping("/user")
    public String postUser(@RequestBody User user) {
        userRepository.save(user);
        return String.format("Saving a new user #%s: %s", user.getUuid(), user.toString());
    }
}