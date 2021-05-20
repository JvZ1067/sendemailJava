package com.jacode.javamaildemo.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.jacode.javamaildemo.R;
import com.jacode.javamaildemo.model.ConfigurationModel;

public class ConfigurationProvider {

    /**
     * READ DATA OF THE MEMORY
     * @param context
     * @return
     */
    public static ConfigurationModel ReadConfiguration(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        );
        String  host = sharedPref.getString(context.getString(R.string.HOST_NAME), "null");
        int port = sharedPref.getInt(context.getString(R.string.PUERTO_VALUE),0);
        String username = sharedPref.getString(context.getString(R.string.USERNAME_VALUE), "null");
        String password = sharedPref.getString(context.getString(R.string.PASSWORD_VALUE), "null");
        return new ConfigurationModel(host, port, username, password);
    }

    /**
     * SAVE DATA IN MEMORY
     * @param context
     * @param config
     * @return
     */
    public static boolean WriteConfiguration(Context context, ConfigurationModel config) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor sharedPref=context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        sharedPref.putString(context.getString(R.string.HOST_NAME), config.getHost());
        sharedPref.putInt(context.getString(R.string.PUERTO_VALUE), config.getPort());
        sharedPref.putString(context.getString(R.string.USERNAME_VALUE), config.getUsername());
        sharedPref.putString(context.getString(R.string.PASSWORD_VALUE), config.getPassword());
        sharedPref.apply();
        return true;
    }
}
