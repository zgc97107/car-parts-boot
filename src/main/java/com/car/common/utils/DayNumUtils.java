package com.car.common.utils;

import java.util.Calendar;

public class DayNumUtils {

    public static String getTimestamp(){
        Calendar cal=Calendar.getInstance();
        String y=String.valueOf(cal.get(Calendar.YEAR));
        String m=String.valueOf(cal.get(Calendar.MONTH)+1);
        String d=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String h=String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String mi=String.valueOf(cal.get(Calendar.MINUTE));
        if(m.length()==1){
            m="0"+m;
        }
        if(d.length()==1){
            d="0"+d;
        }
        if(h.length()==1){
            h="0"+h;
        }
        if(mi.length()==1){
            mi="0"+mi;
        }
        String timestamp=y+m+d+h+mi;
        return timestamp;
    }

}
