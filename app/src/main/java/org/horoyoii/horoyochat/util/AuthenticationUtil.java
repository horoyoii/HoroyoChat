package org.horoyoii.horoyochat.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Horoyoii on 2018.07.29
 */

public class AuthenticationUtil {
    private static String email;
    private static FirebaseUser user;
    public static void init(FirebaseUser users){
        user =users;
    }

    public static String getUserEmail(){
        return user.getEmail();
    }
    public static String getUserUid(){
        return user.getUid();
    }


}
