package de.tk.annapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

//Utility Class
public class Util {

    //fastest way to round a float to a certain scale
    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    public int getAccentColor(Context c){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = c.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);

        return typedValue.data;
    }
}
