package com.supryaga.validators;

import com.supryaga.exceptions.OfficeHoursFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Игорь on 21.09.2015.
 */
public class OfficeHoursValidator implements Validator {

    private static final String OFFICE_HOURS_REG_EXPR = "[\\d]{4}";

    private Pattern pattern = Pattern.compile(OFFICE_HOURS_REG_EXPR);
    private Matcher matcher;
    private String startTime;
    private String endTime;

    public OfficeHoursValidator(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean check() throws OfficeHoursFormatException {
        if(checkHours(startTime) && checkHours(endTime)) {
            if ((Integer.parseInt(endTime) - Integer.parseInt(startTime)) > 0) {
                return true;
            } else {
                throw new OfficeHoursFormatException("End time should be greater than start time");
            }
        }
        return false;
    }

    public boolean checkHours(String hours) {
        matcher = pattern.matcher(hours);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
