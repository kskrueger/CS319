package com.cyscheduler;

import com.cyscheduler.util.PreReqParsing;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CyController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PlanRepository planRepository;

    public CyController(UserRepository ur, CourseRepository cr, PlanRepository pr) {
        userRepository = ur;
        courseRepository = cr;
        planRepository = pr;
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
    @GetMapping("/plan/user/{userId}")
    public String getUserPlans(@PathVariable Integer userId) {
        return new Gson().toJson(planRepository.findByUuid(userId).toArray());
    }

    @CrossOrigin
    @GetMapping("/plan/{planId}")
    public Plan getPlan(@PathVariable Integer planId) {
        return planRepository.findByUpid(planId);
    }

    @CrossOrigin
    @GetMapping("/plan/name/{planName}")
    public Plan getPlanName(@PathVariable String planName) {
        return planRepository.findByName(planName);
    }

    @CrossOrigin
    @GetMapping("/plans")
    public String getPlans() {
        return new Gson().toJson(planRepository.findAll().toArray());
    }

    @CrossOrigin
    @PostMapping("/plan")
    public String postPlan(@RequestBody Plan plan) {
        System.out.println(plan.getName());
        planRepository.save(plan);
        return String.format("Saving a new plan #%d: %s", plan.getUpid(), plan.getName());
    }

    @CrossOrigin
    @GetMapping("/course/{courseId}")
    public Course getCourse(@PathVariable String courseId) {
        System.out.print("COURSE: "+courseId);
        Course course = new Course();
        course.fillCourse(courseId);
        return course;
    }

    @CrossOrigin
    @GetMapping("/courses")
    public String getCourses() {
        return new Gson().toJson(courseRepository.findAll());
    }

    @CrossOrigin
    @GetMapping("/courses/{dept}")
    public String getDeptCourses(@PathVariable String dept) {
        System.out.println("COURSE: "+dept);
        return new Gson().toJson(courseRepository.findByDept(dept.toUpperCase()));
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
                Course course = new Course();
                course.fillCourse(courseName);
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