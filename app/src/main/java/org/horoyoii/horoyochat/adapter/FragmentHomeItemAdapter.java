package org.horoyoii.horoyochat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.horoyoii.horoyochat.ImageCacheDownloader;
import org.horoyoii.horoyochat.R;
import org.horoyoii.horoyochat.app.HoroyoChatApp;
import org.horoyoii.horoyochat.model.FragmentHomeItemClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHomeItemAdapter extends RecyclerView.Adapter<FragmentHomeItemAdapter.ViewHolder> {

    ArrayList<FragmentHomeItemClass> items = new ArrayList<>();

    OnItemClickListener listener;
    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public FragmentHomeItemAdapter(){

    }

    @Override
    public void onBindViewHolder(@NonNull FragmentHomeItemAdapter.ViewHolder viewHolder, int i) {
        FragmentHomeItemClass item  = items.get(i);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public FragmentHomeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) HoroyoChatApp.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;

        itemView = inflater.inflate(R.layout.fragment_home_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    public FragmentHomeItemClass getItem(int position){
        return items.get(position);
    }

    public void addItem(FragmentHomeItemClass item){
        items.add(item);
    }

    public void addItems(ArrayList<FragmentHomeItemClass> items){
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        CircleImageView imageView;
        ImageCacheDownloader downloader;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.Fragment_home_item_name);
            imageView = (CircleImageView) itemView.findViewById(R.id.Fragment_home_item_image);

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


        public void setItem(FragmentHomeItemClass item){
            name.setText(item.getName());
            String value = item.getImage();
            if(value.equals(String.valueOf(R.drawable.user1))){
                imageView.setImageResource(R.drawable.user1);
            }else
                //Picasso.get().load(value).into(imageView);
                downloader = new ImageCacheDownloader(imageView, value);
                downloader.execute();

        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

    }
}
