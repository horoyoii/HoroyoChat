package org.horoyoii.horoyochat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.horoyoii.horoyochat.model.ChatRoomItemClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChatRoomActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_send;
    EditText editText_send;
    ChatRoomItemAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mRootRef = database.getReference("chat");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        recyclerView = (RecyclerView) findViewById(R.id.chatroom_RecyclerView);

        btn_send = (Button) findViewById(R.id.chatroom_btn_send);
        editText_send = (EditText) findViewById(R.id.chatroom_editText_send);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Email은 식별자의 역할
        adapter = new ChatRoomItemAdapter(getApplicationContext(), user.getEmail());



        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new ChatRoomItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChatRoomItemAdapter.ViewHolder holder, View view, int position) {
                ChatRoomItemClass item = adapter.getItem(position);


                Toast.makeText(getApplicationContext(), item.getName(), 0).show();
            }
        });

        if (user != null) {
            // User is signed in
            String email = user.getEmail();
            Toast.makeText(getApplicationContext(), email, 0).show();


        } else {
            // No user is signed in
        }

        // 메세지 전송 ==============================================================================
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                String formatDate = df.format(c.getTime());
                /*
                DatabaseReference myRef = mRootRef.child(formatDate);
                String email = user.getEmail();

                ChatRoomItemClass comment = new ChatRoomItemClass(email,email, formatDate, R.drawable.user1, editText_send.getText().toString());

                myRef.setValue(comment);
                   */

                editText_send.setText("");

            }
        });


        // 채팅 comment 읽어오기

        mRootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ChatRoomItemClass chat = dataSnapshot.getValue(ChatRoomItemClass.class);
                Log.d("HoroyoChat", "읽어온 챗 & onChildAdded호출" +chat.getContent());

                adapter.addItem(chat);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
                adapter.notifyItemInserted(adapter.getItemCount()-1);




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
