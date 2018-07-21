package org.horoyoii.horoyochat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChatListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_send;
    EditText editText_send;
    ItemAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        btn_send = (Button)findViewById(R.id.btn_send);
        editText_send = (EditText)findViewById(R.id.editText_send);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new ItemAdapter(getApplicationContext());

        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민2", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민3", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민4", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));
        adapter.addItem(new ItemClass("이영민", "지금", R.drawable.user1, "줄거리"));


        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemAdapter.ViewHolder holder, View view, int position) {
                ItemClass item = adapter.getItem(position);


                Toast.makeText(getApplicationContext(), item.getName(), 0).show();
            }
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String email = user.getEmail();
            Toast.makeText(getApplicationContext(),email, 0).show();


        } else {
            // No user is signed in
        }


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





    }
}
