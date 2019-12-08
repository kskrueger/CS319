package com.cyscheduler;

import com.cyscheduler.util.PreReqParsing;
import com.cyscheduler.util.Website;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ucid;

    private String courseInput;
    private String url;
    @JsonIgnore
    private transient Website website;
    private ArrayList<ArrayList<String>> prereqs = new ArrayList<ArrayList<String>>();
    private String fullName, courseNumber, realName, dept, number, semesterString, credits, description;
    private Boolean availFall, availSpring;

    public Course() {

    }

    @Override
    public String toString() {
        return String.format("id: %s, username: %s", courseNumber, fullName);
    }

    void fillCourse(String courseNumber) {
        courseInput = courseNumber;
        this.url = "http://catalog.iastate.edu/search/?P="+courseNumber.replace(" ", "%20");
        this.website = new Website(url);
        this.courseNumber = courseNumber;
        this.fullName = getFullName();
        this.prereqs = getPrereqsArrayList(getPrereqsRaw());
        this.realName = getRealName();
        this.dept = getDept();
        this.number = getNumber();
        this.description = getDescription();
        this.semesterString = getSemesterString();
        this.credits = getCredits();
        this.availFall = availableFall();
        this.availSpring = availableSpring();
    }

/*    public Integer getUcid() {
        return ucid;
    }

    public void setUcid(Integer ucid) {
        this.ucid = ucid;
    }*/

    public ArrayList<ArrayList<String>> getPrereqs() {
        return prereqs;
    }

    public Boolean getAvailSpring() {
        return availSpring;
    }

    public Boolean getAvailFall() {
        return availFall;
    }

    enum Semester {SPRING, FALL, BOTH, UNKNOWN}

    public String getUrl() {
        return this.url;
    }

    public boolean courseFound() {
        return getCourseNumber().equals(courseInput);
    }

    String getCourseNumber() {
        int colonPosition = getFullName().indexOf(":");
        return getFullName().substring(0,colonPosition);
    }

    private String getFullName() {
        return website.getText("#fssearchresults > div:nth-child(1) > h2");
    }

    private String getRealName() {
        int colonPosition = getFullName().indexOf(":");
        if ((colonPosition+2) > 0) {
            return getFullName().substring(colonPosition+2);
        } else {
            return "??? ERROR";
        }
    }

    private String getDept() {
        int colonPosition = getFullName().indexOf(":");
        return getFullName().substring(0,colonPosition-4);
    }

    private String getNumber() {
        int colonPosition = getFullName().indexOf(":");
        return getFullName().substring(colonPosition-3,colonPosition);
    }

    private String getPrereqsRaw() {
        String str = website.getText("#fssearchresults > div:nth-child(1) > div > div > p.prereq > em");
        if (Objects.equals(str, "")) {
            return "";
        }
        //int startPos = str.indexOf("");
        //int endPos = str.indexOf(".");
        //return str.substring(startPos);
        return str;
    }

    private String getDescription() {
        String str = website.getText("#fssearchresults > div:nth-child(1) > div > div > p.prereq");
        int startPos = str.indexOf(".")+2;
        return str.substring(startPos);
    }

    private String getSemesterString() {
        return website.getText("#fssearchresults > div:nth-child(1) > div > div > p.credits.noindent");
    }

    public String getCredits() {
        int crIndex = getSemesterString().indexOf("Cr. ")+4;
        String substring = getSemesterString().substring(crIndex);
        int dotIndex = substring.indexOf(".");
        return substring.substring(0,dotIndex);
    }

    public Semester getSemester() {
        if (availableFall()) {
            if (availableSpring()) {return Semester.BOTH;}
            return Semester.FALL;
        }
        if (availableSpring()) {return Semester.SPRING;}
        return Semester.UNKNOWN;
    }

    private boolean availableFall() {
        return getSemesterString().contains("F.") || getSemesterString().contains("R.");
    }

    private boolean availableSpring() {
        return getSemesterString().contains("S.") || getSemesterString().contains("R.");
    }

    private ArrayList<ArrayList<String>> getPrereqsArrayList (String input) {
        PreReqParsing preReqParsing = new PreReqParsing();
        return preReqParsing.parse(input);
    }

    public String getCourseInput() {
        return courseInput;
    }

    public void setCourseInput(String courseInput) {
        this.courseInput = courseInput;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPrereqs(ArrayList<ArrayList<String>> prereqs) {
        this.prereqs = prereqs;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSemesterString(String semesterString) {
        this.semesterString = semesterString;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailFall(Boolean availFall) {
        this.availFall = availFall;
    }

    public void setAvailSpring(Boolean availSpring) {
        this.availSpring = availSpring;
    }
}
