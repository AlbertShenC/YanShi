package info.albertcode.utils;

import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class Number {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }
}
