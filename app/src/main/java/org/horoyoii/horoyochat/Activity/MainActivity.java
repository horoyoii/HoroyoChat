package org.horoyoii.horoyochat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.main_Fragment.Fragment_Friend;
import org.horoyoii.horoyochat.main_Fragment.Fragment_Home;
import org.horoyoii.horoyochat.main_Fragment.Fragment_Profile;
import org.horoyoii.horoyochat.model.FragmentHomeItemClass;

/**
 * Created by Horoyoii on 2018.07.29
 */

public class MainActivity extends AppCompatActivity {
    Fragment_Home fragment_home;
    Fragment_Friend fragment_friend;
    Fragment_Profile fragment_profile;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_home).commit();
                    return true;
                case R.id.navigation_friend:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_friend).commit();
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_profile).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fragment_home = new Fragment_Home();
        fragment_friend = new Fragment_Friend();
        fragment_profile = new Fragment_Profile();


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HoroyoChatApp.getInstance(), "친구추가기능", Toast.LENGTH_SHORT).show();
                //TODO : 친구추가 화면
                Intent intent = new Intent(HoroyoChatApp.getInstance(), FindFriendActivity.class);
                startActivity(intent);

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_home).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ccdh","onStart Call");
        //TODO : 프래래그먼트의 어뎁터에 최신화를 알려준다.
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_home).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("FLOW", "main activiry - onStop Called");
    }
}
