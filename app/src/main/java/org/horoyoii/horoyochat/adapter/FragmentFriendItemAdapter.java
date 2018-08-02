package org.horoyoii.horoyochat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.model.FragmentFriendItemClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentFriendItemAdapter extends RecyclerView.Adapter<FragmentFriendItemAdapter.ViewHolder> {

    ArrayList<FragmentFriendItemClass> items = new ArrayList<>();

    OnItemClickListener listener;
    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public FragmentFriendItemAdapter(){

    }


    @Override
    public void onBindViewHolder(@NonNull FragmentFriendItemAdapter.ViewHolder viewHolder, int i) {
        FragmentFriendItemClass item  = items.get(i);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public FragmentFriendItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) HoroyoChatApp.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        itemView = inflater.inflate(R.layout.fragment_friend_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    public FragmentFriendItemClass getItem(int position){
        return items.get(position);
    }

    public void addItem(FragmentFriendItemClass item){
        items.add(item);
    }

    public void addItems(ArrayList<FragmentFriendItemClass> items){
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }










    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView time;
        CircleImageView imageView;
        TextView content;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.Fragment_friend_item_name);
            time = (TextView)itemView.findViewById(R.id.Fragment_friend_item_time);
            content = (TextView)itemView.findViewById(R.id.Fragment_friend_item_content);
            imageView = (CircleImageView) itemView.findViewById(R.id.Fragment_friend_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }


        public void setItem(FragmentFriendItemClass item){
            name.setText(item.getName());
            //TODO : 시간 줄여서 넣기
            //time.setText(item.getTime());
            // 2018-08-02-14:48:42
            // 14:48
            try{
                String TrimTime = item.getTime().substring(11,16);
                time.setText(TrimTime);
            }catch (Exception e){
                e.printStackTrace();
                time.setText(" ");
            }
            content.setText(item.getContent());
            //TODO : 이미지넣기
            imageView.setImageResource(R.drawable.user1);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

    }
}
