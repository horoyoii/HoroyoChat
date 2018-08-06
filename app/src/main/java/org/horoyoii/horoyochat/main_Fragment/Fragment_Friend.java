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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.horoyoii.horoyochat.Activity.MainActivity;
import org.horoyoii.horoyochat.Activity.ChatRoomActivity;
import org.horoyoii.horoyochat.adapter.FragmentFriendItemAdapter;
import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.model.FragmentFriendItemClass;
import org.horoyoii.horoyochat.model.FragmentHomeItemClass;
import org.horoyoii.horoyochat.util.AuthenticationUtil;
import org.horoyoii.horoyochat.util.FirebaseUtil;

import java.util.ArrayList;


/*
 * setViewContent를 호출할 수 없기에 call back함수인 oncCreateView에서 inflate를 한다.
 */

public class Fragment_Friend extends Fragment {
    final static String TAG = "hohoho";
    final static String NONE = "none";
    MainActivity activity;
    RecyclerView recyclerView;
    FragmentFriendItemAdapter adapter;

    String LastContent;
    String LastTime;
    ArrayList<String> contents = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    int cIdx = 0;
    int tIdx = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_friend, container, false);
        adapter = new FragmentFriendItemAdapter();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.friend_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        loadList();

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FragmentFriendItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FragmentFriendItemAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(HoroyoChatApp.getInstance(), ChatRoomActivity.class);
                FragmentHomeItemClass item = new FragmentHomeItemClass(adapter.getItem(position).getName(),adapter.getItem(position).getImage(),adapter.getItem(position).getUid());
                intent.putExtra("YourData",item);
                startActivity(intent);
            }
        });

        return rootView;
    }
    //TODO : later...
    // 가장 최근의 기록을 보여주는 메서드
    public void loadList(){
        FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(final DataSnapshot src : dataSnapshot.getChildren()){
                    Query lastQuery = FirebaseUtil.getUserRootRef().child(AuthenticationUtil.getUserUid()).child("friend").child(src.getKey()).orderByKey().limitToLast(1);
                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{
                                Log.d(TAG,dataSnapshot.toString());
                                if(dataSnapshot.getChildrenCount() == 0){
                                    LastContent = NONE;
                                    LastTime = NONE;
                                    contents.add(LastContent);
                                    times.add(LastTime);
                                }else{
                                    for(DataSnapshot ss : dataSnapshot.getChildren()){
                                        Log.d("checkss",ss.toString());
                                        if(ss.getValue() == null){
                                            Log.d("check","chatch");
                                        }
                                        LastContent = ss.child("content").getValue().toString();
                                        LastTime = ss.child("time").getValue().toString();
                                        contents.add(LastContent);
                                        times.add(LastTime);
                                    }
                                    Log.d("hee","1223");
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                LastContent = NONE;
                                LastTime = NONE;
                                contents.add(LastContent);
                                times.add(LastTime);
                            }
                            Log.d("hee","123");
                            FirebaseUtil.getUserRootRef().child(src.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    FragmentFriendItemClass item = new FragmentFriendItemClass();
                                    item.setName(dataSnapshot.child("name").getValue().toString());
                                    item.setImage(dataSnapshot.child("profile_image").getValue().toString());
                                    try{
                                        Log.d("hee",contents.get(cIdx)+String.valueOf(cIdx));
                                        item.setUid(dataSnapshot.getKey());
                                        item.setContent(contents.get(cIdx++));
                                        item.setTime(times.get(tIdx++));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    if(item.getContent() !=NONE){
                                        adapter.addItem(item);
                                        adapter.notifyItemInserted(adapter.getItemCount()-1);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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
