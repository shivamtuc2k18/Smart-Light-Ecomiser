package core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
  final static String dateFormat="dd/MM/yyyy";
    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static long getDateInMills(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        long date=0;
        try
        {
             date = simpleDateFormat.parse(dateString).getTime();
            System.out.println("date : "+simpleDateFormat.format(date));
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
        return date;
    }

    public static long getDifferenceInHours(long milliSec){
        long diff=System.currentTimeMillis()-milliSec;
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        if(days<1) {
            long diffHours = diff / (60 * 60 * 1000) % 24;
            return diffHours;
        }
        return 5;
    }
}