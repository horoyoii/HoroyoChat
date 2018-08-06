package org.horoyoii.horoyochat.main_Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.horoyoii.horoyochat.Activity.DetailProfileActivity;
import org.horoyoii.horoyochat.Activity.MainActivity;
import org.horoyoii.horoyochat.ImageCacheDownloader;
import org.horoyoii.horoyochat.adapter.FragmentHomeItemAdapter;
import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.model.FragmentHomeItemClass;
import org.horoyoii.horoyochat.util.AuthenticationUtil;
import org.horoyoii.horoyochat.util.FirebaseUtil;

import de.hdodenhof.circleimageview.CircleImageView;


/*
 * setViewContent를 호출할 수 없기에 call back함수인 oncCreateView에서 inflate를 한다.
 */

public class Fragment_Home extends Fragment {

    MainActivity activity;
    RecyclerView recyclerView;
    TextView textView_myName, friend_list;
    CircleImageView circleImageView_myImage;
    LinearLayout myProfile;
    public static FragmentHomeItemAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView= (RecyclerView)rootView.findViewById(R.id.home_recyclerView);
        textView_myName = (TextView)rootView.findViewById(R.id.Fragment_home_MyName);
        circleImageView_myImage = (CircleImageView)rootView.findViewById(R.id.Fragment_home_MyImage);
        myProfile = (LinearLayout)rootView.findViewById(R.id.Fragment_home_myProfile);
        friend_list = (TextView)rootView.findViewById(R.id.friend_list);

        adapter = new FragmentHomeItemAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView_myName.setText(dataSnapshot.child("name").getValue().toString());
                String value = dataSnapshot.child("profile_image").getValue().toString();
                //TODO : 느려...
                if(value.equals("2131165334")){
                    circleImageView_myImage.setImageResource(R.drawable.user1);
                    Log.d("aaaaa",value);
                }else{
                    Picasso.get().load(value).into(circleImageView_myImage);
                    AuthenticationUtil.setProfile_uri(value);
//                    ImageCacheDownloader downloader = new ImageCacheDownloader(circleImageView_myImage, value);
//                    downloader.execute();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HoroyoChatApp.getInstance(), DetailProfileActivity.class);
                startActivity(intent);
            }
        });

        loadList();

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FragmentHomeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FragmentHomeItemAdapter.ViewHolder holder, View view, int position) {
                FragmentHomeItemClass item = adapter.getItem(position);
                Intent intent = new Intent(HoroyoChatApp.getInstance(), DetailProfileActivity.class);
                intent.putExtra("user",item);
                startActivity(intent);
            }
        });

        return rootView;
    }


    public void loadList(){
        FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot src : dataSnapshot.getChildren()){
                    //Log.d("check",src.getValue().toString());
                    FirebaseUtil.getUserRootRef().child(src.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            FragmentHomeItemClass item = new FragmentHomeItemClass();
                            item.setName(dataSnapshot.child("name").getValue().toString());
                            item.setImage(dataSnapshot.child("profile_image").getValue().toString());
                            item.setUid(dataSnapshot.getKey());

                            adapter.addItem(item);
                            adapter.notifyItemInserted(adapter.getItemCount()-1);

                            String str ="친구 목록                                                                          "+String.valueOf(adapter.getItemCount())+" 명";
                            friend_list.setText(str);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
