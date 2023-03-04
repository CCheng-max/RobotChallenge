package au.com.insigniafinancial.challege.robot;

public class Utils {
    public static boolean isBlank(String s){
        return (s == null || "".equals(s.trim()));
    }
    public static boolean isEmpty(String s){
        return (s == null || "".equals(s.trim()));
    }
    public static String trimLeftAndRight(String s){
        if(s == null){
            return null;
        }
        s = s.replaceAll("^\\s+", "");
        s = s.replaceAll("\\s+$", "");
        return s;
    }
}
