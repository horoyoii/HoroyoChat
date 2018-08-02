package org.horoyoii.horoyochat.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.model.UserClass;
import org.horoyoii.horoyochat.util.AuthenticationUtil;
import org.horoyoii.horoyochat.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindFriendActivity extends AppCompatActivity {
    public static String TAG = "CheckCheck";
    EditText editText;
    Button button, button_add;
    TextView textView;
    String friendUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();
        setContentView(R.layout.activity_find_friend);

        editText = (EditText)findViewById(R.id.find_friend_EditText);
        button = (Button)findViewById(R.id.find_friend_button);
        textView = (TextView)findViewById(R.id.find_friend_result);
        button_add= (Button)findViewById(R.id.find_friend_add_button);
        button_add.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.getUserRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i =0;
                        try {
                            Log.d(TAG, dataSnapshot.getValue().toString());
                            for (DataSnapshot sn : dataSnapshot.getChildren()) {
                                Log.d(TAG, sn.getKey().toString());
                                Log.d(TAG, sn.getValue().toString());

                                if (editText.getText().toString().equals(sn.child("email").getValue().toString())) {
                                    textView.setText(sn.child("name").getValue().toString() + "\n" + sn.child("email").getValue().toString());
                                    friendUid = sn.getKey().toString();

                                    button_add.setVisibility(View.VISIBLE);

                                    break;
                                }
                                i++;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            textView.setText("email을 입력해주세요...");
                        }
                        if(i == dataSnapshot.getChildrenCount()){
                            textView.setText("일치하는 정보가 없습니다...");
                            button_add.setVisibility(View.INVISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }



        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  사용자의 friend 필드에 친구 UID 추가하기
                FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("friend").child(friendUid).setValue(0);
                //TODO : 중복해서 친구가 추가됨...!

                Toast.makeText(HoroyoChatApp.getInstance(),"추가되었습니다.",0).show();
                finish();

//                FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        UserClass userClass = dataSnapshot.getValue(UserClass.class);
//                        userClass.AddFriend(friendUid);
//                        //FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("friend").setValue(userClass.getFriend());
//                        FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("friend").child(friendUid).setValue(0);
//                        //TODO : 중복해서 친구가 추가됨...!
//
//                        Toast.makeText(HoroyoChatApp.getInstance(),"추가되었습니다.",0).show();
//                        finish();
//                        /*
//                    DatabaseReference myRef = mRootRef.child(formatDate);
//                    String email = user.getEmail();
//
//                    ChatRoomItemClass comment = new ChatRoomItemClass(email,email, formatDate, R.drawable.user1, editText_send.getText().toString());
//
//                    myRef.setValue(comment);
//                   */
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


            }
        });
    }
}
