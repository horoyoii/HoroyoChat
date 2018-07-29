package org.horoyoii.horoyochat.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
* Created by Horoyoii on 2018.07.29
 */

public class FirebaseUtil {
    private static DatabaseReference mRootRef;

    public static void init(){
         mRootRef= FirebaseDatabase.getInstance().getReference("user");
    }

    public static void Send(String comment){
        mRootRef.setValue(comment);
    }

    public static DatabaseReference getUserRootRef(){
        return mRootRef.child(AuthenticationUtil.getUserUid());
    }

}
