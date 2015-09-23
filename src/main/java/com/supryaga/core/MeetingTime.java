package com.supryaga.core;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Игорь on 22.09.2015.
 */
public class MeetingTime implements Comparable {

    private static final int MAX_DURATION = 5;

    private Date meetingStartTime;
    private int duration;

    public MeetingTime() {
    }

    public MeetingTime(Date meetingStartTime, int duration) {
        this.meetingStartTime = meetingStartTime;
        this.setDuration(duration);
    }

    public Date getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(Date meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 0 || duration > MAX_DURATION) {
            throw new IllegalArgumentException("Duration should be greater that 0 and less than " + MAX_DURATION);
        }
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingTime)) return false;

        MeetingTime that = (MeetingTime) o;

        if (dateComponentsEquals(meetingStartTime, that.meetingStartTime)) {
            if (isOverlaped(that)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(meetingStartTime).hashCode();
    }

    public Date getMeetingEndTime() {
        return DateUtils.addHours(meetingStartTime, duration);
    }

    private boolean isOverlaped(MeetingTime that) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String startTimeFormatted = simpleDateFormat.format(meetingStartTime);
        String endTimeFormatted = simpleDateFormat.format(getMeetingEndTime());
        String startTimeFormattedThat = simpleDateFormat.format(that.meetingStartTime);
        String endTimeFormattedThat = simpleDateFormat.format(that.getMeetingEndTime());
        if ((startTimeFormattedThat.compareTo(endTimeFormatted) < 0) &&
                ((endTimeFormattedThat).compareTo(endTimeFormatted) > 0) ||
                ((endTimeFormattedThat.compareTo(startTimeFormatted) > 0) &&
                        (endTimeFormattedThat.compareTo(endTimeFormatted) <= 0))) {
            return true;
        }
        return false;
    }

    private boolean dateComponentsEquals(Date d1, Date d2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[\\d]{4}-[\\d]{2}-[\\d]{2}");
        return simpleDateFormat.format(d1).compareTo(simpleDateFormat.format(d2)) == 0;
    }

    @Override
    public int compareTo(Object o) {
        MeetingTime meetingTime = (MeetingTime) o;

        if (this.meetingStartTime.before(meetingTime.getMeetingStartTime())) {
            return -1;
        } else if (this.meetingStartTime.after(meetingTime.getMeetingStartTime())) {
            return 1;
        } else {
            return 0;
        }
    }
}
