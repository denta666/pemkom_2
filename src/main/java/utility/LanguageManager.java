package utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static Locale currentLocale = new Locale("id"); // default Indonesia
    private static ResourceBundle bundle = ResourceBundle.getBundle("GUI.Bundle", currentLocale);

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("GUI.Bundle", currentLocale);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}