package org.horoyoii.horoyochat.model;

/**
 * Created by Horoyoii on 2018.07.29
 */
public class ChatRoomItemClass {
    String email;
    String name;
    String time;
    String image;
    String content;


    public ChatRoomItemClass(){

    }

    public ChatRoomItemClass(String email, String name, String time, String image, String content) {
        this.email = email;
        this.name = name;
        this.time = time;
        this.image = image;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

