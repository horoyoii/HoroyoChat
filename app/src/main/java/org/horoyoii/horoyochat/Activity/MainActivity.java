package org.horoyoii.horoyochat.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.main_Fragment.Fragment_Friend;
import org.horoyoii.horoyochat.main_Fragment.Fragment_Home;
import org.horoyoii.horoyochat.main_Fragment.Fragment_Profile;

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
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragment_home = new Fragment_Home();
        fragment_friend = new Fragment_Friend();
        fragment_profile = new Fragment_Profile();


    }

}
