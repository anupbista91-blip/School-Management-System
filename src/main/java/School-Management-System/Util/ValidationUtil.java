/**
 * File: ValidationUtil.java
 * Purpose: Utility validation helpers for input checks.
 */
package School_Management_System.Util;

import java.util.regex.Pattern;

/**
 * Validation utilities: simple email and non-empty checks.
 */
public class ValidationUtil {
    private static final Pattern EMAIL = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");

    /** Validates email format. */
    public static boolean isEmail(String value) {
        return value != null && EMAIL.matcher(value).matches();
    }

    /** Checks non-empty string. */
    public static boolean notEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}