package br.unicamp.ic.timeverde.dino.utils;

import android.util.Patterns;

public class StringUtils {

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
