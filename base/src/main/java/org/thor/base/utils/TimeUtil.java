package org.thor.base.utils;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ICOGN on 2016/9/26.
 */

public class TimeUtil {

    public static final  String YYYY年_MM月DD日_HH时MM分SS     = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final  String YYYY_MM_DD_HH_MM_SS       = "yyyy-MM-dd HH:mm:ss";
    private static final  String HH_MM                     = "HH:mm";
    private static final  String HTTP_WWW_BJTIME_CN        = "http://www.bjtime.cn";//bjTime
    private static final  String HTTP_WWW_BAIDU_COM        = "http://www.baidu.com";//百度
    private static final  String HTTP_WWW_TAOBAO_COM       = "http://www.taobao.com";//淘宝
    private static final String HTTP_WWW_NTSC_AC_CN       = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
    private static final  String HTTP_WWW_360_CN           = "http://www.360.cn";//360
    private static final  String HTTP_WWW_BEIJING_TIME_ORG = "http://www.beijing-time.org";//beijing-time

    private TimeUtil() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * @param time
     * @return
     */
    public static String getTime(int time) {
        int seconds = time % 60;
        time = time / 60;
        int minutes = time % 60;
        time = time / 60;
        int    hour = time;
        String ss   = seconds < 10 ? "0" + seconds : seconds + "";
        String mm   = minutes < 10 ? "0" + minutes : minutes + "";
        String hh   = hour < 10 ? "0" + hour : hour + "";
        return hh + ":" + mm + ":" + ss;
    }

    /**
     * @param time
     * @return
     */
    public static long getIntTime(String time) {
        if (time != null) {
            String   timeString = time.trim();
            String[] strings    = timeString.split(":");
            return Integer.valueOf(strings[0]) * 60 * 60 + Integer.valueOf(strings[1]) *
                    60 + Integer.valueOf(strings[2]);
        }
        return 0;
    }

    /**
     * @param longTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long longTime, String formatType) throws ParseException {
        Date date = new Date(longTime);
        return stringToDate(dateToString(date, formatType), formatType);
    }

    /**
     * date转换成String
     *
     * @param time
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String time, String formatType) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.parse(time);
    }

    /**
     * @param date
     * @param formatType
     * @return
     */
    public static String dateToString(Date date, String formatType) {
        return new SimpleDateFormat(formatType).format(date);
    }

    /**
     * @param time
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String time, String formatType) throws ParseException {
        Date date = stringToDate(time, formatType);
        if (date == null) return 0;
        return dateToLong(date);
    }

    /**
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * @param time
     * @return
     * @throws ParseException
     */
    public static String longToString(long time, String formatType) throws ParseException {
        return dateToString(longToDate(time, formatType), formatType);
    }

    /**
     * @param time
     * @return
     * @throws ParseException
     */
    public static String stringToString(String time) throws ParseException {
        return longToString(stringToLong(time, YYYY_MM_DD_HH_MM_SS), HH_MM);
    }

    /**
     * @param time
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static int stringToInt(String time, String formatType) throws ParseException {
        long l = stringToLong(time, formatType) / 1000;
        return (int) l;
    }

    public static String getWebDateTime() {
        try {
            URL           url = new URL(HTTP_WWW_NTSC_AC_CN);// 取得资源对象
            URLConnection uc  = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate() / 1000;// 读取网站日期时间
            return String.valueOf(ld);
//            Date date = new Date(ld);// 转换为标准时间对象
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
//            return sdf.format(date);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
