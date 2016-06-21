package com.example.kateryna.chestnut;

import android.net.Uri;
import android.provider.ContactsContract;

import java.util.List;

/**
 * Created by Kateryna on 6/19/2016.
 */
public class RestList {


    private String _name, _address, _stars, _phone;
    private Uri _imageUri;
    private int _id;
    public RestList(int id, String name, String phone, String stars, String address, Uri imageUri){
        _address = address;
        _name = name;
        _stars = stars;
        _phone = phone;
        _imageUri = imageUri;
        _id=id;
    }
public String getName(){
    return _name;
}
    public String getPhone(){
        return _phone;
    }
    public String getAddress(){
        return _address;
    }
    public String getStars(){
        return _stars;
    }
    public Uri get_imageUri() {return _imageUri; }

    public int getId() {
        return _id;
    }
}
