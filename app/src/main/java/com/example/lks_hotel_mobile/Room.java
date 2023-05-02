package com.example.lks_hotel_mobile;

public class Room {
    private int id;
    private String roomNumber;

    public Room(int id, String roomNumber){
        this.id  = id;
        this.roomNumber = roomNumber;
    }

    public int getId(){
        return id;
    }
    public String getRoomNumber(){
        return roomNumber;
    }
}
