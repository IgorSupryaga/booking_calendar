package com.supryaga;

import com.supryaga.core.CalendarRecord;
import com.supryaga.exceptions.BookingFormatException;
import com.supryaga.exceptions.BookingCalendarHelperException;
import com.supryaga.helpers.BookingCalendarHelper;
import com.supryaga.printer.BookingCalendarPrinter;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by Игорь on 20.09.2015.
 */
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException, ParseException {
        try {
            BookingCalendarHelper fr = new BookingCalendarHelper("schedule.txt");
            fr.getCompanyBooking();
            Map<Date, Set<CalendarRecord>> map = fr.getBookingCalendar();
            BookingCalendarPrinter bookingCalendarPrinter = new BookingCalendarPrinter();
            bookingCalendarPrinter.print(map);
//            for (Map.Entry e : map.entrySet()) {
//                System.out.println(e.getKey());
//                Set<CalendarRecord> calendarRecords = (Set<CalendarRecord>) e.getValue();
//                for(CalendarRecord calendarRecord : calendarRecords) {
//                    System.out.println(calendarRecord.getMeetingTime().getMeetingStartTime());
//                }
//            }
//            System.out.println();
        } catch (FileNotFoundException e) {
            throw new BookingCalendarHelperException(e.getMessage(), e);
        } catch (BookingFormatException e) {
            e.printStackTrace();
        }




//        String s1 = "2011-03-21 06:14";
//        String s2 = "2011-03-21 09:14";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
//        MeetingTime mt1 = new MeetingTime(simpleDateFormat.parse(s1), 4);
//        System.out.println("ISOVERL:" + mt1.equals(new MeetingTime(simpleDateFormat.parse(s2), 3)));
//
//        System.out.println(new DateHelper().formatToMeetingDate(simpleDateFormat.parse(s1)));
//
//        Date d1 = simpleDateFormat.parse(s1);
//        Date d2 = simpleDateFormat.parse(s2);
//        System.out.println(simpleDateFormat2.format(d1).compareTo(simpleDateFormat2.format(d2)));




//        String s = "2011-03-21 09:00 2";
//        final String durRegex = "[\\d]{1,2}$";
//        final String dateRegex = "[\\d]{4}-[\\d]{2}-[\\d]{2}[\\s]+[\\d]{2}:[\\d]{2}:[\\d]{2}";
//        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Pattern pattern = Pattern.compile(durRegex);
//        Matcher matcher = pattern.matcher(s);
//        if (matcher.find()) {
//            System.out.println(matcher.group());
//        }
    }
}
