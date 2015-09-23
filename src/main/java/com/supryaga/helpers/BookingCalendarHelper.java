package com.supryaga.helpers;

import com.supryaga.core.*;
import com.supryaga.exceptions.BookingCalendarHelperException;
import com.supryaga.exceptions.BookingFormatException;
import com.supryaga.exceptions.OfficeHoursFormatException;
import com.supryaga.exceptions.SubmissionTimeFormatException;
import com.supryaga.validators.MeetingTimeValidator;
import com.supryaga.validators.OfficeHoursValidator;
import com.supryaga.validators.SubmissionTimeValidator;
import com.supryaga.validators.Validator;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Игорь on 20.09.2015.
 */
public class BookingCalendarHelper {

    private InputStream inputStream;
    private Reader reader;
    private SimpleDateFormat simpleDateFormat;
    private BufferedReader bufferedReader;
    private CompanyBooking companyBooking;
    private CalendarRecord bookingRequest;
    private Validator validator;
    private Map<Date, Set<CalendarRecord>> bookingCalendar;
    private Set<CalendarRecord> calendarRecords;
    private CalendarRecord calendarRecord;


    public BookingCalendarHelper(String fileName) throws FileNotFoundException {
         inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
//        inputStream = new FileInputStream(classLoader.getResource(fileName).getFile());
        reader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(reader);
        companyBooking = new CompanyBooking();
        bookingCalendar = new TreeMap();
    }

    public Map<Date, Set<CalendarRecord>> getCompanyBooking() {
        String line;
        boolean isHoursProcessed = false;
        boolean isRequestSubmission = false;
        boolean isMeetingStartTime = false;
        boolean isDigit = false;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.length() > 1) {
                    isDigit = Character.isDigit(line.charAt(1));
                    if (isDigit && isHoursProcessed == false) {
                        setHours(line);
                        isHoursProcessed = true;
                        isRequestSubmission = true;
                        continue;
                    } else if (isDigit && isRequestSubmission) {
                        bookingRequest = new CalendarRecord();
                        setSubmissionTime(line);
                        isRequestSubmission = false;
                        isMeetingStartTime = true;
                        continue;
                    } else if (isDigit && isMeetingStartTime) {
                        setMeetingTime(line);
                        if (!isPassTimeBounds()) {
                            Date meetingDate = formatToMeetingDate(bookingRequest.getMeetingTime().getMeetingStartTime());
                            if (bookingCalendar.containsKey(meetingDate)) {
                                Set<CalendarRecord> recordsByDate = bookingCalendar.get(meetingDate);
                                for (CalendarRecord record : recordsByDate) {
                                    if (!record.getMeetingTime().equals(bookingRequest.getMeetingTime())) {
                                        recordsByDate.add(new CalendarRecord(bookingRequest.getMeetingTime(), bookingRequest.getSubmissionTime()));
                                    } else if(record.getMeetingTime().equals(bookingRequest.getMeetingTime())) {
                                        if (record.getSubmissionTime().compareTo(bookingRequest.getSubmissionTime()) > 0) {
                                            recordsByDate.clear();
                                            recordsByDate.add(new CalendarRecord(bookingRequest.getMeetingTime(), bookingRequest.getSubmissionTime()));
                                        }
                                    }
                                }
                            } else {
                                calendarRecords = new TreeSet();
                                calendarRecord = new CalendarRecord(bookingRequest.getMeetingTime(), bookingRequest.getSubmissionTime());
                                calendarRecords.add(calendarRecord);
                                bookingCalendar.put(meetingDate, calendarRecords);
                            }
                        }
                        isMeetingStartTime = false;
                        isRequestSubmission = true;
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            throw new BookingCalendarHelperException(e.getMessage(), e);
        }
        return bookingCalendar;
    }

    public Map<Date, Set<CalendarRecord>> getBookingCalendar() {
        return bookingCalendar;
    }

    private void setHours(String line) {
        String[] officeHours = line.split("[\\s]+");
        if (officeHours.length == 2) {
            validator = new OfficeHoursValidator(officeHours[0], officeHours[1]);
            if (validator.check()) {
                companyBooking.setStartTime(officeHours[0]);
                companyBooking.setEndTime(officeHours[1]);
            } else {
                throw new OfficeHoursFormatException("Hours should contain 4 digits");
            }
        } else {
            throw new BookingFormatException("Should contain only start and end hours");
        }
    }

    private void setSubmissionTime(String line) throws BookingFormatException {
        validator = new SubmissionTimeValidator(line);
        if (validator.check()) {
            submTimeSetFields(line);
        }
    }

    private void submTimeSetFields(String line) {
        final String empIdRegex = "EMP[\\d]{3,}";
        final String submissionDateRegex = "[\\d]{4}-[\\d]{2}-[\\d]{2}[\\s]+[\\d]{2}:[\\d]{2}:[\\d]{2}";
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Matcher matcher = getMatcher(empIdRegex, line);
        SubmissionTime submissionTime = new SubmissionTime();
        if (matcher.find()) {
            submissionTime.setEmployeeId(matcher.group());
        }
        matcher = getMatcher(submissionDateRegex, line);
        if (matcher.find()) {
            try {
                submissionTime.setSubmissionDate(simpleDateFormat.parse(matcher.group()));
            } catch (ParseException e) {
                throw new SubmissionTimeFormatException(e.getMessage());
            }
        }
        bookingRequest.setSubmissionTime(submissionTime);
    }

    private void setMeetingTime(String line) {
        validator = new MeetingTimeValidator(line);
        if (validator.check()) {
            meetingTimeSetFields(line);
        }
    }

    private void meetingTimeSetFields(String line) {
        final String durationRegex = "[\\d]{1,2}$";
        final String meetingDateRegex = "[\\d]{4}-[\\d]{2}-[\\d]{2}[\\s]+[\\d]{2}:[\\d]{2}";
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Matcher matcher = getMatcher(durationRegex, line);
        MeetingTime meetingTime = new MeetingTime();
        if (matcher.find()) {
            meetingTime.setDuration(Integer.parseInt(matcher.group()));
        }
        matcher = getMatcher(meetingDateRegex, line);
        if (matcher.find()) {
            try {
                meetingTime.setMeetingStartTime(simpleDateFormat.parse(matcher.group()));
            } catch (ParseException e) {
                throw new SubmissionTimeFormatException(e.getMessage());
            }
        }
        bookingRequest.setMeetingTime(meetingTime);
    }

    private Matcher getMatcher(String regex, String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher;
    }

    private boolean isPassTimeBounds() {
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        String endTime = companyBooking.getEndTime();
        Date boundary;
        try {
            boundary = simpleDateFormat.parse(endTime.substring(0, 2) + ":" + endTime.substring(2, 4));
        } catch (ParseException e) {
            throw new BookingCalendarHelperException(e.getMessage(), e);
        }
        return simpleDateFormat.format(bookingRequest.getMeetingTime().getMeetingEndTime()).compareTo(simpleDateFormat.format(boundary)) > 0;
    }

    public Date formatToMeetingDate(Date date) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            throw new BookingCalendarHelperException(e.getMessage(), e);
        }
    }
}
