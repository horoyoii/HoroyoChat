package org.horoyoii.horoyochat.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
* Created by Horoyoii on 2018.07.29
 */

public class FirebaseUtil {
    private static FirebaseDatabase mRootRef;

    public static void init(){
         mRootRef= FirebaseDatabase.getInstance();
    }

    public static void Send(String comment){

    }

    public static DatabaseReference getUserRootRef(){
        return mRootRef.getReference("user");
    }

//    public static DatabaseReference getUserInfoRef(){
//        return mRootRef.getReference("userInfo");
//    }


}
