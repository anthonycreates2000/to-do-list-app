package com.example.keepoapp.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Formatter {
    public String getHumanReadableDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String humanReadableDate = format.format(date);

        return humanReadableDate;
    }
}
