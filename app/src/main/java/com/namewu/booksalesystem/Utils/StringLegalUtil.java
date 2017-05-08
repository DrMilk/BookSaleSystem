package com.namewu.booksalesystem.Utils;

/**
 * Created by Administrator on 2017/3/19.
 */

public class StringLegalUtil {
    public static boolean isCorrectPhonenum(String s){
        if(s.length()==11&&isCorrectNumer(s))
            return true;
        return false;
    }
    public static boolean isCorrectEmail(String s){
        if(s.contains("@")&&s.contains(".com")&&!isCorrectNumer(s))
            return true;
        return false;
    }
    public static boolean isCorrectNumer(String str) {
        for(int i=str.length();--i>=0;) {
        int chr=str.charAt(i);
        if(chr<48 || chr>57)
        return false;
        }
        return true;
    }
    public static boolean isSafePassword(String s){
        if(s.length()>6&&!isCorrectNumer(s))
            return true;
        return false;
    }
    public static boolean isHaveLength(String s){
        if(s.length()>0)
            return true;
        return false;
    }
}
