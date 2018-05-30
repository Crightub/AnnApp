package de.tk.annapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

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
            ret.set(Integer.valueOf(datest[2]), Integer.valueOf(datest[1]) - 1, Integer.valueOf(datest[0]));
            return ret;
        }
        return null;
    }


    public static void createPushNotification(Context context, int ID, String title, String subject, int smallIcon, Bitmap largeIcon) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "AnnApp";
            String description = "AnnApp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(ID), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

        /*NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(subject)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(subject))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/

        System.out.println("Start Notification");
        /*Notification noti = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(subject)
                .setSmallIcon(smallIcon)
                .setLargeIcon(largeIcon)
                .build();*/


        NotificationCompat.Builder notif = new NotificationCompat.Builder(context)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(smallIcon)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(subject);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(ID, notif.build());

    }

    public static int getColorPrimary(Context context, Activity activity) {
        int colorSchemePosition = activity.getPreferences(MODE_PRIVATE).getInt("colorSchemePosition", 0);
        int color;
        switch (colorSchemePosition) {
            case 1:
                color = context.getColor(R.color.cs1_primary);
                break;
            case 2:
                color = context.getColor(R.color.cs2_primary);
                break;
            case 3:
                color = context.getColor(R.color.cs3_primary);
                break;
            default:
                color = context.getColor(R.color.colorPrimary);
                break;
        }

        return color;

    }

    public static int getColorAccent(Context context, Activity activity) {
        int colorAccent;
        int colorSchemePosition = activity.getPreferences(MODE_PRIVATE).getInt("colorSchemePosition", 0);

        switch (colorSchemePosition) {
            case 1:
                colorAccent = context.getColor(R.color.cs1_accent);
                break;
            case 2:
                colorAccent = context.getColor(R.color.cs2_accent);
                break;
            case 3:
                colorAccent = context.getColor(R.color.cs3_accent);
                break;
            default:
                colorAccent = context.getColor(R.color.colorAccent);
                break;
        }

        return colorAccent;
    }

    public static int getColorPrimaryDark(Context context, Activity activity) {
        int colorSchemePosition = activity.getPreferences(MODE_PRIVATE).getInt("colorSchemePosition", 0);
        int color;
        switch (colorSchemePosition) {
            case 1:
                color = context.getColor(R.color.cs1_primaryDark);
                break;
            case 2:
                color = context.getColor(R.color.cs2_primaryDark);
                break;
            case 3:
                color = context.getColor(R.color.cs3_primaryDark);
                break;
            default:
                color = context.getColor(R.color.colorPrimaryDark);
                break;
        }

        return color;
    }

    public static int getSubjectColor(Context context, Activity activity, Subject subject) {
        int colorSchemePosition = activity.getPreferences(MODE_PRIVATE).getInt("colorSchemePosition", 0);
        SubjectManager subjectManager = SubjectManager.getInstance();
        int index = subjectManager.getSubjects().indexOf(subject);

        int color = context.getColor(R.color.colorAccent);

        if (colorSchemePosition == 0) {
            color = context.getColor(R.color.colorAccent);
        } else if (colorSchemePosition == 1) {
            switch (index) {
                case 0:
                    color = context.getResources().getColor(R.color.cs1_0);
                    break;
                case 1:
                    color = context.getResources().getColor(R.color.cs1_1);
                    break;
                case 2:
                    color = context.getResources().getColor(R.color.cs1_2);
                    break;
                case 3:
                    color = context.getResources().getColor(R.color.cs1_3);
                    break;
                case 4:
                    color = context.getResources().getColor(R.color.cs1_4);
                    break;
                case 5:
                    color = context.getResources().getColor(R.color.cs1_5);
                    break;
                case 6:
                    color = context.getResources().getColor(R.color.cs1_6);
                    break;
                case 7:
                    color = context.getResources().getColor(R.color.cs1_7);
                    break;
                case 8:
                    color = context.getResources().getColor(R.color.cs1_8);
                    break;
                case 9:
                    color = context.getResources().getColor(R.color.cs1_9);
                    break;
                case 10:
                    color = context.getResources().getColor(R.color.cs1_10);
                    break;
                case 11:
                    color = context.getResources().getColor(R.color.cs1_11);
                    break;
                case 12:
                    color = context.getResources().getColor(R.color.cs1_12);
                    break;
                case 13:
                    color = context.getResources().getColor(R.color.cs1_13);
                    break;
                case 14:
                    color = context.getResources().getColor(R.color.cs1_14);
                    break;
            }
        } else if (colorSchemePosition == 2) {
            switch (index) {
                case 0:
                    color = context.getResources().getColor(R.color.cs2_0);
                    break;
                case 1:
                    color = context.getResources().getColor(R.color.cs2_1);
                    break;
                case 2:
                    color = context.getResources().getColor(R.color.cs2_2);
                    break;
                case 3:
                    color = context.getResources().getColor(R.color.cs2_3);
                    break;
                case 4:
                    color = context.getResources().getColor(R.color.cs2_4);
                    break;
                case 5:
                    color = context.getResources().getColor(R.color.cs2_5);
                    break;
                case 6:
                    color = context.getResources().getColor(R.color.cs2_6);
                    break;
                case 7:
                    color = context.getResources().getColor(R.color.cs2_7);
                    break;
                case 8:
                    color = context.getResources().getColor(R.color.cs2_8);
                    break;
                case 9:
                    color = context.getResources().getColor(R.color.cs2_9);
                    break;
                case 10:
                    color = context.getResources().getColor(R.color.cs2_10);
                    break;
                case 11:
                    color = context.getResources().getColor(R.color.cs2_11);
                    break;
                case 12:
                    color = context.getResources().getColor(R.color.cs2_12);
                    break;
                case 13:
                    color = context.getResources().getColor(R.color.cs2_13);
                    break;
                case 14:
                    color = context.getResources().getColor(R.color.cs2_14);
                    break;
            }
        } else if (colorSchemePosition == 3) {
            switch (index) {
                case 0:
                    color = context.getResources().getColor(R.color.cs3_0);
                    break;
                case 1:
                    color = context.getResources().getColor(R.color.cs3_1);
                    break;
                case 2:
                    color = context.getResources().getColor(R.color.cs3_2);
                    break;
                case 3:
                    color = context.getResources().getColor(R.color.cs3_3);
                    break;
                case 4:
                    color = context.getResources().getColor(R.color.cs3_4);
                    break;
                case 5:
                    color = context.getResources().getColor(R.color.cs3_5);
                    break;
                case 6:
                    color = context.getResources().getColor(R.color.cs3_6);
                    break;
                case 7:
                    color = context.getResources().getColor(R.color.cs3_7);
                    break;
                case 8:
                    color = context.getResources().getColor(R.color.cs3_8);
                    break;
                case 9:
                    color = context.getResources().getColor(R.color.cs3_9);
                    break;
                case 10:
                    color = context.getResources().getColor(R.color.cs3_10);
                    break;
                case 11:
                    color = context.getResources().getColor(R.color.cs3_11);
                    break;
                case 12:
                    color = context.getResources().getColor(R.color.cs3_12);
                    break;
                case 13:
                    color = context.getResources().getColor(R.color.cs3_13);
                    break;
                case 14:
                    color = context.getResources().getColor(R.color.cs3_14);
                    break;
            }
        }

        return color;
    }

}
