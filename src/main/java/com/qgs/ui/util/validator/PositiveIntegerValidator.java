package com.qgs.ui.util.validator;

import com.vaadin.data.validator.AbstractStringValidator;

/**
 *
 * @author rafael
 */
public class PositiveIntegerValidator extends AbstractStringValidator {

    public PositiveIntegerValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    protected boolean isValidValue(String value) {
        try {
            Integer ret = Integer.parseInt((String) value);
            return (ret >= 0);
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
