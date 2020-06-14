package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.app.Application;

public class ConfigApp extends Application {


    //http://10.0.2.2:88
    //http://api.showsite.online
    private static String url_api = "http://api.showsite.online/PHP/ControleurJSON.php";

    public static String getUrl_api() {
        return url_api;
    }

    public static void setUrl_api(String url_api) {
        ConfigApp.url_api = url_api;
    }
}

