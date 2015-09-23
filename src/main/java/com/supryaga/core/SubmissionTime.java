package com.supryaga.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Игорь on 22.09.2015.
 */
public class SubmissionTime implements Comparable {

    private Date submissionDate;
    private String employeeId;

    public SubmissionTime() {
    }

    public SubmissionTime(Date submissionDate, String employeeId) {
        this.submissionDate = submissionDate;
        this.employeeId = employeeId;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubmissionTime)) return false;

        SubmissionTime that = (SubmissionTime) o;

        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        if (submissionDate != null ? !submissionDate.equals(that.submissionDate) : that.submissionDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(submissionDate).hashCode();
    }

    @Override
    public int compareTo(Object o) {
        SubmissionTime submissionTime = (SubmissionTime) o;
        if (this.submissionDate.compareTo(submissionTime.getSubmissionDate()) < 0) {
            return -1;
        } else if (this.submissionDate.compareTo(submissionTime.getSubmissionDate()) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
