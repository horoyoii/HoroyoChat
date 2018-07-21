package org.horoyoii.horoyochat;

public class ItemClass {
    String name;
    String time;
    int image;
    String content;

    public ItemClass(String name, String time, int image, String content) {
        this.name = name;
        this.time = time;
        this.image = image;
        this.content = content;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
