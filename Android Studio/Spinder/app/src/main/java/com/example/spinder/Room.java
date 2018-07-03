package com.example.spinder;

public class Room {
    private int imageResource;
    private String roomName;

    public Room(int imageResource, String roomName){
        this.imageResource = imageResource;
        this.roomName = roomName;
    }

    public int getImageResource(){
        return imageResource;
    }

    public String getRoomName(){
        return roomName;
    }
}
