package com.supryaga.core;

/**
 * Created by Игорь on 22.09.2015.
 */
public class CalendarRecord implements Comparable {

    private MeetingTime meetingTime;
    private SubmissionTime submissionTime;

    public CalendarRecord() {
    }

    public CalendarRecord(MeetingTime meetingTime, SubmissionTime submissionTime) {
        this.meetingTime = meetingTime;
        this.submissionTime = submissionTime;
    }

    public MeetingTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(MeetingTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public SubmissionTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(SubmissionTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarRecord)) return false;
        if (o == null) return false;

        CalendarRecord that = (CalendarRecord) o;

        if (!meetingTime.equals(that.meetingTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meetingTime.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        CalendarRecord calendarRecord = (CalendarRecord) o;
        if (meetingTime.compareTo(calendarRecord.getMeetingTime()) < 0) {
            return -1;
        } else if (meetingTime.compareTo(calendarRecord.getMeetingTime()) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
