package com.wizardlybump17.wpunishments.util;

public class DateUtil {

    public static String getDifferenceBetween(long date1, long date2) {
        String result = "";
        long diff = Math.abs(date1 - date2);
        long seconds = diff / 1000 % 60;
        long minutes = diff / (60 * 1000) % 60;
        long hours = diff / (60 * 60 * 1000) % 24;
        long days = diff / (24 * 60 * 60 * 1000);
        result += days == 0 ? "" : days + " days ";
        result += hours == 0 ? "" : hours + " hours ";
        result += minutes == 0 ? "" : minutes + " minutes ";
        result += seconds == 0 ? "" : seconds + " seconds";
        return result.trim().isEmpty() ? "now" : result.trim();
    }
}
