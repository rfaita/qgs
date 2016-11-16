package com.qgs.util.validation;

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
    
}
