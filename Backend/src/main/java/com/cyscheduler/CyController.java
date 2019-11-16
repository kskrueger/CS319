package com.cyscheduler;

import com.cyscheduler.util.PreReqParsing;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class CyController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public CyController(UserRepository ur, CourseRepository cr) {
        userRepository = ur;
        courseRepository = cr;
    }

    @CrossOrigin
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId) {
        return (User)userRepository.findByUuid(userId).toArray()[0];
    }

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("/course/{courseId}")
    public Course getCourse(@PathVariable String courseId) {
        System.out.print("COURSE: "+courseId);
        Course course = new Course(courseId);
        return course;
    }

    @CrossOrigin
    @GetMapping("/courses")
    public String getCourses() {
        return new Gson().toJson(courseRepository.findAll());
    }

    @CrossOrigin
    @PostMapping("/updateTheDb")
    public String udpateDb(@RequestBody String password) {
        if (!password.equals("Mitra309")) {
            return "You shouldn't be here! Incorrect password to update!";
        }
        ArrayList<String> allCourses = new PreReqParsing().read("/src/main/java/com/cyscheduler/util/CourseList");
        for (String courseName : allCourses) {
            courseName = courseName.replaceAll("[^A-Za-z0-9 ]", "");
            try {
                Course course = new Course(courseName);
                courseRepository.save(course);
                System.out.println("Saved: "+course.getCourseNumber());
            } catch (Exception e) {
                System.out.println("Failed: "+courseName);
            }
        }
        int n = courseRepository.findAll().size();
        return String.format("DB updated with %d courses", n);
    }
}