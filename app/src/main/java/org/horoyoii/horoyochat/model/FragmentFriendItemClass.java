package org.horoyoii.horoyochat.model;

public class FragmentFriendItemClass {
    String name;
    String content;
    String time;
    String Image;
    String Uid;

    public FragmentFriendItemClass(){

    }

    public FragmentFriendItemClass(String name, String content, String time, String image, String uid) {
        this.name = name;
        this.content = content;
        this.time = time;
        Image = image;
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
