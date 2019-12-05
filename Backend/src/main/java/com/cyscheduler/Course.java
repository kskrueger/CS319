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
    private String courseInput;
    private String url;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Integer ucid;
    @JsonIgnore
    private transient Website website;
    private ArrayList<ArrayList<String>> prereqs = new ArrayList<ArrayList<String>>();
    private String fullName, courseNumber, realName, dept, number, semesterString, credits, description;
    private Boolean availFall, availSpring;
    private int x;
    private int y;

    Course() {

    }

    @Override
    public String toString() {
        return String.format("id: %d, username: %s", courseNumber, fullName);
    }

    public Course(String courseNumber) {
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

    public String getCourseNumber() {
        int colonPosition = getFullName().indexOf(":");
        return getFullName().substring(0,colonPosition);
    }

    public String getFullName() {
        return website.getText("#fssearchresults > div:nth-child(1) > h2");
    }

    public String getRealName() {
        int colonPosition = getFullName().indexOf(":");
        if ((colonPosition+2) > 0) {
            return getFullName().substring(colonPosition+2);
        } else {
            return "??? ERROR";
        }
    }

    public String getDept() {
        int colonPosition = getFullName().indexOf(":");
        return getFullName().substring(0,colonPosition-4);
    }

    public String getNumber() {
        int colonPosition = getFullName().indexOf(":");
        return getFullName().substring(colonPosition-3,colonPosition);
    }

    public String getPrereqsRaw() {
        String str = website.getText("#fssearchresults > div:nth-child(1) > div > div > p.prereq > em");
        if (Objects.equals(str, "")) {
            return "";
        }
        //int startPos = str.indexOf("");
        //int endPos = str.indexOf(".");
        //return str.substring(startPos);
        return str;
    }

    public String getDescription() {
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
