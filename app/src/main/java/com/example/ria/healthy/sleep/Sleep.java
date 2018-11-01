package com.example.ria.healthy.sleep;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Sleep implements Comparable<Sleep> {

    private int id;
    private String date;
    private String sleepTime;
    private String wakeupTime;
    private String totalSleepTime;

    public static final String DATABASE_NAME = "my.db";
    public static final int DATABASE_VERSION = 1;

    @Override
    public int compareTo(@NonNull Sleep sleep) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        long otherEpoch, thisEpoch;
        try {
            otherEpoch = fmt.parse(sleep.date).getTime();
            thisEpoch = fmt.parse(this.date).getTime();
            return -Long.compare(thisEpoch, otherEpoch);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String DATE = "date";
        public static final String SLEEP_TIME = "sleep_time";
        public static final String WAKEUP_TIME = "wakeup_time";
        public static final String TOTAL_SLEEP_TIME = "total_sleep_time";
    }

    public Sleep() {
    }

    public Sleep(int id, String date, String sleep_time, String wakeupTime, String totalSleepTime){
        this.id = id;
        this.date = date;
        this.sleepTime = sleep_time;
        this.wakeupTime = wakeupTime;
        this.totalSleepTime = totalSleepTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWakeupTime() {
        return wakeupTime;
    }

    public void setWakeupTime(String wakeupTime) {
        this.wakeupTime = wakeupTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getTotalSleepTime() {
        return totalSleepTime;
    }

    public void setTotalSleepTime(String totalSleepTime) {
        this.totalSleepTime = totalSleepTime;
    }

}
