package org.horoyoii.horoyochat.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import org.horoyoii.horoyochat.ChatRoomActivity;
import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.model.FragmentHomeItemClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProfileActivity extends AppCompatActivity {

    FragmentHomeItemClass item;
    ImageView imageView;
    CircleImageView detail_image;
    ConstraintLayout back;
    TextView detail_name;
    Button btn_chat_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();
        setContentView(R.layout.activity_detail_profile);

        back = (ConstraintLayout)findViewById(R.id.profile_background);
        imageView = (ImageView)findViewById(R.id.profile_back);
        detail_image = (CircleImageView)findViewById(R.id.detail_image);
        detail_name = (TextView)findViewById(R.id.detail_name);
        btn_chat_start = (Button)findViewById(R.id.detail_chat_start);
        DrawableImageViewTarget target = new DrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.back2).into(target);



        Intent passedIntent = getIntent();
        processIntent(passedIntent);
        Log.d("cococo",item.getName()+ item.getImage()+ item.getUid());
        btn_chat_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HoroyoChatApp.getInstance(), ChatRoomActivity.class);
                intent.putExtra("YourData",item);
                startActivity(intent);
            }
        });


        }


        public void processIntent(Intent intent){
            if(intent !=null){
                item = (FragmentHomeItemClass)intent.getParcelableExtra("user");

                detail_name.setText(item.getName());
                Picasso.get().load(item.getImage()).into(detail_image);

            }
        }
}
