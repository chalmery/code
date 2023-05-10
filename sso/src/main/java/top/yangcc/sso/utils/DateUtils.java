package top.yangcc.sso.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


    private static final String FORMAT = "yyyyMMddHHmmssssss";

    public static String format(){
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT);
        return sf.format(new Date());
    }
}
