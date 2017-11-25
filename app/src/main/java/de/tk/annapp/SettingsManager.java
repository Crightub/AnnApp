package de.tk.annapp;

/**
 * Created by Tobias Kiehnlein on 25.11.2017.
 */

public class SettingsManager {

    private static final SettingsManager settingsManager = new SettingsManager();

    private SettingsManager(){
        System.out.println("Create SubjetManager...");
    }

    //Returns the singelton subjectManager
    public static SettingsManager getInstance(){
        return settingsManager;
    }
}
