package org.horoyoii.horoyochat.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Horoyoii on 2018.07.29
 */

public class AuthenticationUtil {
    private static String email;
    private static FirebaseUser user;
    private static String userName;

    public static void init(FirebaseUser users){
        user =users;
        setUserName();
    }

    public static String getUserEmail(){
        return user.getEmail();
    }
    public static String getUserUid(){
        return user.getUid();
    }





    private static void setUserName(){
        //String name;
        FirebaseUtil.getUserRootRef().child(user.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("check",String.valueOf(dataSnapshot));
                userName = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static String getUserName(){
        return userName;
    }



}
