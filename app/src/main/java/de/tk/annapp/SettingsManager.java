package de.tk.annapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {

    private static final SettingsManager settingsManager = new SettingsManager();

    private SettingsManager(){
        System.out.println("Create SettingsManager...");
    }

    //Returns the singelton settingsManager
    public static SettingsManager getInstance(){
        return settingsManager;
    }

}
