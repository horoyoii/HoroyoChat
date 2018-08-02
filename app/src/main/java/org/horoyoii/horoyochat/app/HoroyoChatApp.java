package org.horoyoii.horoyochat.app;

import android.app.Application;

/*
* Created by Horoyoii on 2017.07.30
 */


// Application Class . . .
public class HoroyoChatApp extends Application {
    private static HoroyoChatApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static HoroyoChatApp getInstance(){
        return instance;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
