package com.example.kindler.util;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.kindler.models.UserDetailModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 7horseinfo on 22/02/17.
 */

public class PreferencesService {

    private static final String PREFS_NAME = "goldpocket";
    private static PreferencesService mSingleton = new PreferencesService();
    private static Context mContext;


    public static final String Login_Status = "Login_Status";
    public static final String USERSTATUS = "USERSTATUS";
    public static final String Main_service = "Main_service";

    private PreferencesService() {

    }

    public static PreferencesService instance() {
        return mSingleton;
    }

    public static void init(Context context) {

        mContext = context;
    }

    public SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void ClearPreference() {
        SharedPreferences settings = getPrefs();
        settings.edit().clear().commit();
    }

    public void saveLogin_Status(String login_Status){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(Login_Status, login_Status);
        editor.commit();
    }

    public String getLogin_Status(){
        return getPrefs().getString(Login_Status, "");
    }


    public void saveUserDetails(ArrayList<UserDetailModel> serviceTypes) {
        SharedPreferences.Editor editor = getPrefs().edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(serviceTypes);
        editor.putString(USERSTATUS, jsonFavorites);
        editor.commit();
    }

    public ArrayList<UserDetailModel>  getUserDetails() {
        List<UserDetailModel> favorites;
        if (getPrefs().contains(USERSTATUS)) {
            String jsonFavorites = getPrefs().getString(USERSTATUS, null);
            Gson gson = new Gson();
            UserDetailModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    UserDetailModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<UserDetailModel>(favorites);
        } else
            return null;

        return (ArrayList<UserDetailModel>) favorites;
    }

}