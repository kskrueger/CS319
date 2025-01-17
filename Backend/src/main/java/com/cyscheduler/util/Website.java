package com.cyscheduler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Website {
    private Document site;

    public Website(String url) {
        try {
            site = Jsoup.connect(url).get();
        } catch (IOException e) {
            //e.printStackTrace();
            //Course not found!
        }
    }

    public String getText(String path) {
        try {
            return site.select(path).first().text();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("PAGE DOES NOT EXIST! RIPPPP");
            return "";
        }
    }

}
