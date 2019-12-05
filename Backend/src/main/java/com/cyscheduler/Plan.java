package com.cyscheduler;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer upid;
    private String name;
    private Integer uuid;
    private ArrayList<ArrayList<Course>> semestersCourses = new ArrayList<>();

    public Integer getUpid() {
        return upid;
    }

    public void setUpid(Integer upid) {
        this.upid = upid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public ArrayList<ArrayList<Course>> getSemestersCourses() {
        return semestersCourses;
    }

    public void setSemestersCourses(ArrayList<ArrayList<Course>> semestersCourses) {
        this.semestersCourses = semestersCourses;
    }
}
