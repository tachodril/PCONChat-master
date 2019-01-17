package com.chat.pcon.pconchat.Models;


import com.google.firebase.Timestamp;

public class MessageInfo {
    public String msg;
    public String uid;
    public String name;
    public Timestamp timestamp;
    public String color;
    public boolean isReceived=true; //if false then sent msg and true then received msg
    public MessageInfo(){}

    public MessageInfo(String msg,String uid, String name,
                      Timestamp timestamp,
                       boolean isReceived,String color){
        this.msg = msg;
        this.uid = uid;
        this.name = name;
         this.timestamp = timestamp;
        this.isReceived = isReceived;
        this.color = color;
    }
}
