package com.example.prilogulka.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Time {

    public static String getTodayTime(){
        Date date = new Date();

        return date.getHours()+":"+date.getMinutes();
    }

    public static String getToday(){
        Date date = new Date();

        return date.getDay()+"."+date.getMonth()+"."+date.getYear();
    }
}
