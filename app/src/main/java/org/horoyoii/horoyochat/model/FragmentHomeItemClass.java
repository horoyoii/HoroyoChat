package org.horoyoii.horoyochat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FragmentHomeItemClass implements Parcelable{
    String name;
    String image;
    String uid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(uid);
    }

    protected FragmentHomeItemClass(Parcel in){
        name = in.readString();
        image = in.readString();
        uid = in.readString();
    }

    public static final Creator<FragmentHomeItemClass> CREATOR = new Creator<FragmentHomeItemClass>() {
        @Override
        public FragmentHomeItemClass createFromParcel(Parcel parcel) {
            return new FragmentHomeItemClass(parcel);
        }

        @Override
        public FragmentHomeItemClass[] newArray(int i) {
            return new FragmentHomeItemClass[i];
        }
    };


    public FragmentHomeItemClass() {

    }

    public FragmentHomeItemClass(String name, String image, String uid) {
        this.name = name;
        this.image = image;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
