package edu.android.android_lab2_2;

import android.app.Application;

public class ConfigApp extends Application {


    //http://10.0.2.2:88
    //http://api.showsite.online
    private static String url_api = "http://10.0.2.2:88/PHP/ControleurJSON.php";

    public static String getUrl_api() {
        return url_api;
    }

}

