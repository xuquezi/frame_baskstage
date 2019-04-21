package com.example.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTest {
    public static void main(String[] args) {
        Date date = new Date();
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//多态
        String format = bf.format(date);
        System.out.println(format);

    }
}
