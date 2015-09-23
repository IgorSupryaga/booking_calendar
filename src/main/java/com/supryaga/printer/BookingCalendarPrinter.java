package com.supryaga.printer;

import com.supryaga.core.CalendarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by Игорь on 22.09.2015.
 */
public class BookingCalendarPrinter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public void print(Map<Date, Set<CalendarRecord>> bookingCalendar) {
        System.out.println("---OUTPUT---\n");
        for (Map.Entry e : bookingCalendar.entrySet()) {
            System.out.println(dateFormat.format(e.getKey()));
            Set<CalendarRecord> calendarRecords = (Set<CalendarRecord>) e.getValue();
            for(CalendarRecord calendarRecord : calendarRecords) {
                System.out.println(timeFormat.format(calendarRecord.getMeetingTime().getMeetingStartTime()) + " " +
                        timeFormat.format(calendarRecord.getMeetingTime().getMeetingEndTime()) + " " + calendarRecord.getSubmissionTime().getEmployeeId());
            }
        }
    }
}
