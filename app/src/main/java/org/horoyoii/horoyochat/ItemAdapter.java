package org.horoyoii.horoyochat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;

    ArrayList<ItemClass> items = new ArrayList<>();

    OnItemClickListener listener;

    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public ItemAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.chat_list_item, viewGroup, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ItemClass item  = items.get(i);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    public ItemClass getItem(int position){
        return items.get(position);
    }


    public void addItem(ItemClass item){
        items.add(item);
    }

    public void addItems(ArrayList<ItemClass> items){
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

            name = (TextView)itemView.findViewById(R.id.item_name);
            time = (TextView)itemView.findViewById(R.id.item_time);
            content = (TextView)itemView.findViewById(R.id.item_content);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);

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


        public void setItem(ItemClass item){
            name.setText(item.getName());
            time.setText(item.getTime());
            content.setText(item.getContent());
            imageView.setImageResource(item.getImage());
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

    }

}
