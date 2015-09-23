package com.supryaga.validators;

import com.supryaga.exceptions.BookingFormatException;
import com.supryaga.exceptions.OfficeHoursFormatException;

/**
 * Created by Игорь on 21.09.2015.
 */
public interface Validator {
    boolean check() throws BookingFormatException;
}
