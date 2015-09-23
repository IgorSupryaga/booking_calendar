package com.supryaga.core;

import java.util.*;

/**
 * Created by Игорь on 21.09.2015.
 */
public class CompanyBooking {
    private String startTime;
    private String endTime;
    private Map<Date, CalendarRecord> bookingRequests;

    public CompanyBooking() {
        bookingRequests = new TreeMap();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Map<Date, CalendarRecord> getBookingRequests() {
        return bookingRequests;
    }

    public void setBookingRequests(Map<Date, CalendarRecord> bookingRequests) {
        this.bookingRequests = bookingRequests;
    }
}
