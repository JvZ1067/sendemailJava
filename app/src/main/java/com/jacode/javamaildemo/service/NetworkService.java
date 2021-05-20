package com.jacode.javamaildemo.service;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkService {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
