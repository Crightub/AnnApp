package de.tk.annapp;

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
