package org.horoyoii.horoyochat.util;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Horoyoii on 2018.07.29
 */


public class StorageUtil {
    private static StorageReference Ref;

    public static void init(){
        Ref = FirebaseStorage.getInstance().getReference().child("users");
    }

    public static StorageReference getRootStorage(){
        return Ref;
    }


}
