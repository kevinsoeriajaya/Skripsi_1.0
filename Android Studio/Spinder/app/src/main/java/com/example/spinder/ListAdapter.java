package com.example.spinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    private Context context;
    private List<RoomData> roomList;

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mRoomName;
        private ImageView mRoomImage;

        public ListViewHolder(View itemView){
            super(itemView);
            mRoomName = (TextView) itemView.findViewById(R.id.roomName);
            mRoomImage = (ImageView) itemView.findViewById(R.id.roomImage);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v){

        }

    }


    public ListAdapter(List<RoomData> roomList){
        //this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final RoomData room = roomList.get(position);
        holder.mRoomName.setText(room.getTitle());

        //mRoomName.setText(RoomData.roomName[position]);
        //mRoomImage.setImageResource(RoomData.picturePath[position]);
        Picasso.get()
                .load(room.getUrl())
                .resize(50, 50)
                .centerCrop()
                .placeholder(R.drawable.badminton_logo)
                .into(holder.mRoomImage);
    }

    @Override
    public int getItemCount() {

        return roomList.size();
    }


}
