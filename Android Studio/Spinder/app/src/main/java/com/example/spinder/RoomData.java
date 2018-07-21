package com.example.spinder;

public class RoomData {
    /*public static  String[] roomName = new String[]{
            "room 1",
            "room 2",
            "room 3",
            "room 4",
            "room 5",
            "room 6",
            "room 7",
            "room 8",
            "room 9",
            "room 10"
    };

    public static int[] picturePath = new int[]{
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes,
            R.drawable.tes
    };
    public static String[] urls = new String[]{
            "https://i.imgur.com/rSHWaam.png",
            "https://i.imgur.com/usp2LHl.png",
            "https://i.imgur.com/VhLo63O.png",
            "https://i.imgur.com/3J8Ws2o.png",
            "https://i.imgur.com/3J8Ws2o.png",
            "https://i.imgur.com/rSHWaam.png",
            "https://i.imgur.com/usp2LHl.png",
            "https://i.imgur.com/VhLo63O.png",
            "https://i.imgur.com/3J8Ws2o.png",
            "https://i.imgur.com/rSHWaam.png"
    };*/

    private String id;
    private String title;
    private String location;
    private String time;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
