package com.qgs.ui.util.validator;

import com.vaadin.data.validator.AbstractStringValidator;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author rafael
 */
public class DoubleValidator extends AbstractStringValidator {

    public DoubleValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    protected boolean isValidValue(String value) {
        try {

            NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setRoundingMode(RoundingMode.HALF_UP);

            nf.parse(value);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

}
