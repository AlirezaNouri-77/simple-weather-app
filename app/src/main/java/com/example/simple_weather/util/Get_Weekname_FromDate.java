package com.example.simple_weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Get_Weekname_FromDate {

    // return name of week by date for example 2022/01/15 return saturday

    public String get_week_name(String input) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = simpleDateFormat.parse(input);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE", Locale.getDefault());
        return simpleDateFormat1.format(date);
    }
}
