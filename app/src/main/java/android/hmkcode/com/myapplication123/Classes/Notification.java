package android.hmkcode.com.myapplication123.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;

import com.google.gson.Gson;

/**
 * Created by Ahmed Hamdy on 6/19/2016.
 */
public class Notification {

    private String id;
    private String msg;
    private String type;
    private String teamID;
    private String targetUserId;
    private String teamName;



    public Notification(){

    }

    public Notification(String id,String type, String msg,String teamID){
        this.id = id;
        this.type= type;
        this.msg = msg;
        this.teamID = teamID;

    }

    public Notification(String id,String type, String msg, String teamID, String targetUserId, String teamName){
        this.id = id;
        this.type= type;
        this.msg = msg;
        this.teamID = teamID;
        this.targetUserId = targetUserId;
        this.teamName = teamName;

    }

    public Notification(String type, String msg, String teamID, String targetUserId, String teamName){
        this.type= type;
        this.msg = msg;
        this.teamID = teamID;
        this.targetUserId = targetUserId;
        this.teamName = teamName;

    }


    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }








}
