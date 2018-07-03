package com.example.spinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends RecyclerView.Adapter{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return RoomData.roomName.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mRoomName;
        private ImageView mRoomImage;

        public ListViewHolder(View itemView){
            super(itemView);
            mRoomName = (TextView) itemView.findViewById(R.id.roomName);
            mRoomImage = (ImageView) itemView.findViewById(R.id.roomImage);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            mRoomName.setText(RoomData.roomName[position]);
            mRoomImage.setImageResource(RoomData.picturePath[position]);
        }

        public void onClick(View v){

        }

    }
}
