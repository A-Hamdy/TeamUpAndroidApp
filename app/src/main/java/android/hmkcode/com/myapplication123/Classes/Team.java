package android.hmkcode.com.myapplication123.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ahmed Hamdy on 6/11/2016.
 */
public class Team  {
    
    private String id;
    private String title;
    private String bio;
    private String description;
    private String image;
    private int durtion;
    private int ownerId;
    private int skillId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDurtion() {
        return durtion;
    }

    public void setDurtion(int durtion) {
        this.durtion = durtion;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }



    public void teamSaveData(Context context, Team team) throws JSONException {

        Gson jsonBuilder = new Gson();
        JSONObject json = new JSONObject(jsonBuilder.toJson(team));
        SharedPreferences.Editor editor = context.getSharedPreferences("TEAMPrefs", context.MODE_PRIVATE).edit();
        editor.putString("teamJson", json.toString());
        editor.commit();
    }


    public Team teamGetData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("TEAMPrefs", context.MODE_PRIVATE);
        String myJsonText = prefs.getString("teamJson", null);
        Team userData = null;

        if (myJsonText != null) {
            Gson jsonBuilder = new Gson();
            userData = new Team();
            userData = jsonBuilder.fromJson(myJsonText, Team.class);
        }
        return userData;
    }


}
