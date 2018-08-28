package com.example.prilogulka;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManager {

    public final String SHARED_PREFERENCES_NAME = "userInfo";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public String getStringFromSharedPreferences(String keyInSharedPreferences) {
        return sharedPreferences.getString(keyInSharedPreferences, "");
    }

    public void putStringInSharedPreferences(String key, String stringToPut) {
        editor.putString(key, stringToPut);
        editor.commit();
    }

    public void initUserInfoStorer(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}
