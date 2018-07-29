package org.horoyoii.horoyochat;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.horoyoii.horoyochat.model.ChatRoomItemClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomItemAdapter extends RecyclerView.Adapter<ChatRoomItemAdapter.ViewHolder> {
    private static final String TAG = "horoyochat_debug";
    private static final int ITEM_VIEW_TYPE_MINE = 1;
    private static final int ITEM_VIEW_TYPE_YOURS = 2;



    Context context;

    ArrayList<ChatRoomItemClass> items = new ArrayList<>();
    String myEmail; // 나인지 남인지의 식별자


    OnItemClickListener listener;

    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public ChatRoomItemAdapter(Context context, String mail){
        this.context = context;
        this.myEmail = mail;
    }


    @Override
    public int getItemViewType(int position) {

        if(items.get(position).getEmail().equals(myEmail)){
            return ITEM_VIEW_TYPE_MINE;
        }else{
            return ITEM_VIEW_TYPE_YOURS;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    // 위의 getItemViewType을 오버라이드하여 position을 return해야 아래의 onCreateViewHolder에서의 position이 0,1,2,3 .. .으로 나오게 된다.



    //================================================================================
    // ViewHolder는 리사이클러뷰 아이템 하나당 한번씩 inflation 을 한다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        Log.d(TAG, "inflate"+items.get(viewType)+" "+String.valueOf(viewType));


        //=====================================================================
        // 내가 보낸 것인 경우 오른쪽 그렇지 않은 경우 왼쪽의 layout을 inflate한다.

        if(viewType == ITEM_VIEW_TYPE_MINE){
            itemView = inflater.inflate(R.layout.chat_room_talk_item_reverse, viewGroup, false);
        }else{
            itemView = inflater.inflate(R.layout.chat_room_talk_item, viewGroup, false);
        }


        //itemView = inflater.inflate(R.layout.chat_room_talk_item, viewGroup, false);
        return new ViewHolder(itemView);
    }








    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChatRoomItemClass item  = items.get(i);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    public ChatRoomItemClass getItem(int position){
        return items.get(position);
    }


    public void addItem(ChatRoomItemClass item){
        items.add(item);
    }

    public void addItems(ArrayList<ChatRoomItemClass> items){
        this.items = items;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView time;
        CircleImageView imageView;
        TextView content;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.ChatRoom_item_name);
            time = (TextView)itemView.findViewById(R.id.ChatRoom_item_time);
            content = (TextView)itemView.findViewById(R.id.ChatRoom_item_content);
            imageView = (CircleImageView) itemView.findViewById(R.id.ChatRoom_item_image);

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


        public void setItem(ChatRoomItemClass item){
            name.setText(item.getName());
            time.setText(item.getTime());
            content.setText(item.getContent());
            imageView.setImageResource(R.drawable.user1);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

    }

}
