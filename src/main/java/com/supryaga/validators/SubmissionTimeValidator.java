package com.supryaga.validators;

import com.supryaga.exceptions.BookingFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Игорь on 21.09.2015.
 */
public class SubmissionTimeValidator implements Validator {

    private static final String SUBM_TIME_REG_EXPR = "[\\d]{4}-[\\d]{2}-[\\d]{2}[\\s]+[\\d]{2}:[\\d]{2}:[\\d]{2}[\\s]+EMP[\\d]{3}";

    private Pattern pattern = Pattern.compile(SUBM_TIME_REG_EXPR);
    private Matcher matcher;
    private String line;

    public SubmissionTimeValidator(String line) {
        this.line = line;
    }

    @Override
    public boolean check() throws BookingFormatException {
        matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return true;
        } else {
            throw new BookingFormatException("Submission time must be in format yyyy-mm-dd hh:mm:ss EMPID");
        }
    }
}
