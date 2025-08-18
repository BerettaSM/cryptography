package com.ramon.crypt.util;

public class CPFUtils {

    public static String standardize(String cpfString) {
        return cpfString.replaceAll("\\D", "");
    }

}
