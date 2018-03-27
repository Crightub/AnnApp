package de.tk.annapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//Utility Class
public class Util {

    //fastest way to round a float to a certain scale
    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / pow;
    }

    //I'm against!
    public int getAccentColor(Context c) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = c.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);

        return typedValue.data;
    }

    public static String getDateString(Calendar date) {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR) != date.get(Calendar.YEAR))
            return new SimpleDateFormat("dd.MM.yyyy").format(date.getTime());
        else if (now.get(Calendar.WEEK_OF_YEAR) != date.get(Calendar.WEEK_OF_YEAR))
            return new SimpleDateFormat("dd.MM").format(date.getTime());
        return getWeekDayShort(date);
    }

    public static String getWeekDayShort(Calendar date) {
        switch (date.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "MO";
            case Calendar.TUESDAY:
                return "Di";
            case Calendar.WEDNESDAY:
                return "Mi";
            case Calendar.THURSDAY:
                return "Do";
            case Calendar.FRIDAY:
                return "Fr";
            case Calendar.SATURDAY:
                return "Sa";
            case Calendar.SUNDAY:
                return "So";
            default:
                return "Error:getWeekDayShort";
        }
    }

    public static int calendarWeekdayByDayIndex(int day) {
        switch (day) {
            case 0:
                return Calendar.MONDAY;
            case 1:
                return Calendar.TUESDAY;
            case 2:
                return Calendar.WEDNESDAY;
            case 3:
                return Calendar.THURSDAY;
            case 4:
                return Calendar.FRIDAY;
            case 5:
                return Calendar.SATURDAY;
            case 6:
                return Calendar.SUNDAY;
            default:
                return -1;
        }
    }

    public static String getFullDate(Calendar calendar) {
        return new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
    }

    public static Calendar getCalendarFromFullString(String fullCalendar) {
        if (fullCalendar.matches("\\d*\\.\\d*\\.\\d*")) {
            String[] datest = fullCalendar.split("\\.");
            Calendar ret = Calendar.getInstance();
            ret.set(Integer.valueOf(datest[2]), Integer.valueOf(datest[1]), Integer.valueOf(datest[0]));
            return ret;
        }
        return null;
    }
}
