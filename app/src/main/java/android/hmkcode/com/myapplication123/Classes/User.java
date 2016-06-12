package android.hmkcode.com.myapplication123.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.hmkcode.com.myapplication123.Utitlites.Utilites;

/**
 * Created by Ahmed Hamdy on 5/30/2016.
 */
public class User {


//    private static User userSingleton = null;
//
//    private User(){ }
//
//    public static User getInstance(){
//        if(userSingleton == null)
//            userSingleton = new User();
//
//        return userSingleton;
//    }


    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String bio;
    private String token;
    private String linkedIn;
    private String profilePictureBase64;
    //private byte[] profilePictureBase64;

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getProfilePictureBase64() {
        return profilePictureBase64;
    }

    public void setProfilePictureBase64(String profilePictureBase64) {
        this.profilePictureBase64 = profilePictureBase64;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void userSaveData(Context context, String json) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Utilites.SharedPref_Name, context.MODE_PRIVATE).edit();
        editor.putString("userJson", json);
        editor.commit();
    }


    public User userGetData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Utilites.SharedPref_Name, context.MODE_PRIVATE);
        String myJsonText = prefs.getString("userJson", null);
        User userData = null;
        User user;
        if (myJsonText != null) {
            Gson jsonBuilder = new Gson();
            userData = new User();
            userData = jsonBuilder.fromJson(myJsonText, User.class);
        }
        return userData;
    }


    public void imageSaveToInternal(Context context, Bitmap b, String picName) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("TAG", "io exception");
            e.printStackTrace();
        }


    }

    public Bitmap imageGetFromInternal(Context context, String picName) {
        Bitmap b = null;
        FileInputStream fis;
        try {
            fis = context.openFileInput(picName);
            b = BitmapFactory.decodeStream(fis);
            fis.close();

        } catch (FileNotFoundException e) {
            Log.d("TAG", "file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("TAG", "io exception");
            e.printStackTrace();
        }
        return b;
    }


}
