package org.horoyoii.horoyochat.main_Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.horoyoii.horoyochat.Activity.MainActivity;
import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.util.AuthenticationUtil;
import org.horoyoii.horoyochat.util.FirebaseUtil;
import org.horoyoii.horoyochat.util.StorageUtil;

import java.io.ByteArrayOutputStream;


/**
 * Created by Horoyoii on 2018.07.29
 */



/*
 * setViewContent를 호출할 수 없기에 call back함수인 oncCreateView에서 inflate를 한다.
 */

public class Fragment_Profile extends Fragment {

    MainActivity activity;
    ImageView profile_image;
    TextView name;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);


        profile_image = (ImageView)rootView.findViewById(R.id.profile_image);
        name = (TextView)rootView.findViewById(R.id.textView_name);
        FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child("name").getValue().toString();
                name.setText(Name);
                String value = dataSnapshot.child("profile_image").getValue().toString();
                Picasso.get().load(value).into(profile_image);
                //TODO : 느려


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1001);
            }
        });


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1001){

            try {
                Uri image = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                profile_image.setImageBitmap(bitmap);
                uploadImage();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }


    public void uploadImage(){

        SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String stEmail = preferences.getString("email","");

        StorageReference Ref = StorageUtil.getRootStorage().child(stEmail+".jpg");
        Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = Ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // 서버에 저장된 이미지의 uri를 가져오는 코드
                StorageUtil.getRootStorage().child(stEmail+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // uri를 realtime database에 추가한다.
                        FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("profile_image").setValue(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });
    }


}
