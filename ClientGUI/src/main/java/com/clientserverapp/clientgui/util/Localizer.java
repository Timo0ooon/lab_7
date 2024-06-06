package com.clientserverapp.clientgui.util;

import com.clientserverapp.clientgui.Bundles.Languages;
import com.clientserverapp.clientgui.Environment.UserData;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {
    private final String language;
    private ResourceBundle bundle;

    public Localizer(String language) {
        this.language = language;
        this.setBundle();
    }

    private void setBundle() {
        HashMap<String, Languages> values = new HashMap<>();
        HashMap<Languages, ResourceBundle> locales = new HashMap<>();

        values.put("Русский", Languages.Russian);
        values.put("Српски", Languages.Serbian);
        values.put("Latviski", Languages.Latvian);
        values.put("English", Languages.English);

        locales.put(Languages.Russian, ResourceBundle.getBundle("com.clientserverapp.clientgui.Bundles.Language", new Locale("ru")));
        locales.put(Languages.English, ResourceBundle.getBundle("com.clientserverapp.clientgui.Bundles.Language", new Locale("en")));
        locales.put(Languages.Latvian, ResourceBundle.getBundle("com.clientserverapp.clientgui.Bundles.Language", new Locale("lv")));
        locales.put(Languages.Serbian, ResourceBundle.getBundle("com.clientserverapp.clientgui.Bundles.Language", new Locale("sr")));

        UserData.language = values.get(this.language);

        this.bundle = locales.get(UserData.language);
    }

    public String getValue(String key) {
        return this.bundle.getString(key);
    }

    public String getLanguage() {
        return language;
    }

}
