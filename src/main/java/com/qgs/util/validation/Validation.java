package com.qgs.util.validation;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author rafael
 */
public class Validation {

    public static Integer validInteger(String v) {
        if (v == null || v.isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(v);
        }
    }

    public static Double validDouble(String v) throws ParseException {
        if (v == null || v.isEmpty()) {
            return null;
        } else {
            NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setRoundingMode(RoundingMode.HALF_UP);

            return (Double) nf.parse(v).doubleValue();
        }
    }

}
