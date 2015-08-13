//this is a comment added by me

package com.example.everaldo.hackssms;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by everaldo on 25-June-2015
 */
public class Preference {

    private final static String SP_NAME = "sms_hack";

    private SharedPreferences sp;
    public Preference(Context context){
        sp = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUrl(String url){

        SharedPreferences.Editor edit = sp.edit();
        edit.putString("url", url);
        edit.commit();
    }

    public String getUrl(){
        String url = sp.getString("url", "http://localhost/shutdown.php");
        return url;
    }

    public void clearPreference(){

        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }
}
