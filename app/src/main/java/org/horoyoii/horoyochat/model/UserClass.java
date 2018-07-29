package org.horoyoii.horoyochat.model;

/**
 * Created by Horoyoii on 2018.07.29
 */

public class UserClass {
    String name;
    String email;
    String profile_image; // uri 로 저장한다.
    String chat;

    public UserClass(String name, String email, String profile_image , String chat) {
        this.name = name;
        this.email = email;
        this.profile_image = profile_image;
        this.chat = chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
