package com.cyscheduler;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer upid;
    private String name;
    private Integer uuid;
    //@Column
    //@ElementCollection(targetClass=CourseNode.class)
    //@OneToMany
    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "uaid")
    private ArrayList<ArrayList<String>> semestersCourses = new ArrayList<>();
    //private Course semestersCourses;// = new ArrayList<>();

    public Plan() {

    }

    Integer getUpid() {
        return upid;
    }

    public void setUpid(Integer upid) {
        this.upid = upid;
    }

    String getName() {
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

    public ArrayList<ArrayList<String>> getSemestersCourses() {
        return semestersCourses;
    }

    public void setSemestersCourses(ArrayList<ArrayList<String>> semestersCourses) {
        this.semestersCourses = semestersCourses;
    }
}
