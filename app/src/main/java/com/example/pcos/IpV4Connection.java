package com.example.pcos;

import android.content.Context;
import android.content.SharedPreferences;

public class IpV4Connection {
    private static final String PREF_NAME = "MyPrefs";
    private static final String CONNECTION_ID_KEY = "connectionId";
    private static String baseUrl = "http://192.168.75.184/pcos_db/";

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        IpV4Connection.baseUrl = baseUrl;
    }

    public static String getUrl(String path) {
        return getBaseUrl() + path;
    }

    private static String connectionId;

    public static String getConnectionId(Context context) {
        if (connectionId == null) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            connectionId = prefs.getString(CONNECTION_ID_KEY, null);
        }
        return connectionId;
    }

    public static void setConnectionId(Context context, String connectionId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CONNECTION_ID_KEY, connectionId);
        editor.apply();
        IpV4Connection.connectionId = connectionId;
    }
}
