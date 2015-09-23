package com.supryaga;

import com.supryaga.core.CalendarRecord;
import com.supryaga.core.MeetingTime;
import com.supryaga.core.SubmissionTime;
import com.supryaga.helpers.BookingCalendarHelper;
import org.junit.Before;
import org.junit.Test;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Игорь on 23.09.2015.
 */
public class BookingCalendarHelperTest {

    private static final String FILE_NAME = "schedule.txt";
    private static final SimpleDateFormat meetingAndSubmission = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat bookingDates = new SimpleDateFormat("yyyy-MM-dd");

    private BookingCalendarHelper bookingCalendarHelper;
    private Map<Date, Set<CalendarRecord>> companyBooking;

    @Before
    public void setUp() {
        try {
            bookingCalendarHelper = new BookingCalendarHelper(FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCompanyBookingSize() {
        companyBooking = bookingCalendarHelper.getCompanyBooking();
        int size = companyBooking.size();
        assertEquals(2, companyBooking.size());
    }

    @Test
    public void getCompanyBookingDates() {
        companyBooking = bookingCalendarHelper.getCompanyBooking();
        List<Date> expectedDates = prepareDates();
        int i = 0;
        for (Map.Entry e : companyBooking.entrySet()) {
            assertEquals(expectedDates.get(i++), e.getKey());
        }
        assertEquals(2, companyBooking.size());
    }

    @Test
    public void companyBookingByDateSize() {
        companyBooking = bookingCalendarHelper.getCompanyBooking();
        List<Date> dates = prepareDates();
        int expectedSize1 = 1;
        int expectedSize2 = 2;
        int actualSize1 = companyBooking.get(dates.get(0)).size();
        int actualSize2 = companyBooking.get(dates.get(1)).size();
        assertEquals(expectedSize1, actualSize1);
        assertEquals(expectedSize2, actualSize2);
    }

    @Test
    public void getCompanyBooking() {
        companyBooking = bookingCalendarHelper.getCompanyBooking();
        List<Date> expectedDates = prepareDates();
        Set<CalendarRecord> calendarRecords1 = companyBooking.get(expectedDates.get(0));
        Set<CalendarRecord> calendarRecords2 = companyBooking.get(expectedDates.get(1));
        assertTrue(calendarRecords1.containsAll(prepareCalendarRecords1()));
        assertTrue(calendarRecords2.containsAll(prepareCalendarRecords2()));
    }

    private List<Date> prepareDates() {
        List<Date> dates = new ArrayList();
        try {
            dates.add(bookingDates.parse("2011-03-21"));
            dates.add(bookingDates.parse("2011-03-22"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    private Set<CalendarRecord> prepareCalendarRecords1() {
        Set<CalendarRecord> calendarRecords = new TreeSet();
        Date expectedMeetingDate1 = new Date();
        Date expectedMeetingDate2 = new Date();;
        Date expectedMeetingDate3 = new Date();;
        Date expectedSubmissionDate1 = new Date();
        Date expectedSubmissionDate2 = new Date();
        Date expectedSubmissionDate3 = new Date();
        try {
            expectedMeetingDate1 = meetingAndSubmission.parse("2011-03-21 09:00");
            expectedSubmissionDate1 = meetingAndSubmission.parse("2011-03-16 12:34:56");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarRecords.add(new CalendarRecord(new MeetingTime(expectedMeetingDate1, 2), new SubmissionTime(expectedSubmissionDate1, "EMP002")));
        return calendarRecords;
    }

    private Set<CalendarRecord> prepareCalendarRecords2() {
        Set<CalendarRecord> calendarRecords = new TreeSet();
        Date expectedMeetingDate1 = new Date();
        Date expectedMeetingDate2 = new Date();
        Date expectedSubmissionDate1 = new Date();
        Date expectedSubmissionDate2 = new Date();
        try {
            expectedMeetingDate1 = meetingAndSubmission.parse("2011-03-22 14:00");
            expectedMeetingDate2 = meetingAndSubmission.parse("2011-03-22 16:00");
            expectedSubmissionDate1 = meetingAndSubmission.parse("2011-03-16 09:28:23");
            expectedSubmissionDate2 = meetingAndSubmission.parse("2011-03-17 10:17:06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarRecords.add(new CalendarRecord(new MeetingTime(expectedMeetingDate1, 2), new SubmissionTime(expectedSubmissionDate1, "EMP003")));
        calendarRecords.add(new CalendarRecord(new MeetingTime(expectedMeetingDate2, 1), new SubmissionTime(expectedSubmissionDate2, "EMP004")));
        return calendarRecords;
    }
}
