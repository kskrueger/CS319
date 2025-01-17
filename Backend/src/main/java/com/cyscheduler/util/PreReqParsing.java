package com.cyscheduler.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

public class PreReqParsing {
    //class to sort array list by index in given string
    class SortByIndex implements Comparator<String> {
        String input;
        public SortByIndex (String input) {
            this.input = input;
        }

        public int compare (String a, String b) {
            return input.indexOf(a) - input.indexOf(b);
        }
    }

    public ArrayList<ArrayList<String>> parse(String input) {
        ArrayList<ArrayList<String>> prereqs = new ArrayList<>();
        long start = System.currentTimeMillis();
        ArrayList<String> allCourses = read("/src/main/java/com/cyscheduler/util/CourseList");
        ArrayList<String> courseList = new ArrayList<>();
        for (String course : allCourses) {
            if (input.toLowerCase().contains(course.toLowerCase())) {
                courseList.add(course);
            }
        }

        //sort list by order in input
        courseList.sort(new SortByIndex(input));

        for (int i = 0; i < courseList.size(); i++) {
            String course = courseList.get(i);

            int indexStart = input.toLowerCase().indexOf(course.toLowerCase());

            String beforeWord = "";
            try {
                //beforeWord = input.substring(indexStart-3,indexStart);
                String prevWord = courseList.get(i-1);
                int prevEnd = input.toLowerCase().indexOf(prevWord.toLowerCase())+prevWord.length();
                beforeWord = input.substring(prevEnd,indexStart);
            } catch (IndexOutOfBoundsException e) {
                //out of bounds because first word
            }

            if (beforeWord.contains(";") || beforeWord.contains(",") || beforeWord.contains("and") || indexStart==0 || i == 0) {
                //if first entry OR there's ;/, before word, create new ArrayList with word and add to master ArrayList
                ArrayList<String> list = new ArrayList<>();
                list.add(course);
                prereqs.add(list);

            } else if (beforeWord.contains("or")) {
                //if there is "or" before the word, get most recent ArrayList from master, and add to it
                prereqs.get(prereqs.size()-1).add(course);
            }
        }

        //System.out.println(prereqs);
        return prereqs;
    }

    public ArrayList read(String fileName) {
        String contents = "";
        String filePath = new File("").getAbsolutePath();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath+fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            contents = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(contents, ArrayList.class);
    }
}
