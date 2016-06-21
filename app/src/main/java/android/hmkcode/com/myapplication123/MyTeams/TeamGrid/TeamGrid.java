package android.hmkcode.com.myapplication123.MyTeams.TeamGrid;

import android.graphics.Bitmap;

/**
 * Created by Lincoln on 18/05/16.
 */
public class TeamGrid {
    private String name;
    private String teamId;
    private String numOfMembers;
    private byte[] thumbnail;
    private boolean owner;


    public TeamGrid() {
    }

    public TeamGrid(String teamId , String name, String numOfMembers, byte[] thumbnail, boolean owner) {
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.thumbnail = thumbnail;
        this.teamId = teamId;
        this.owner = owner;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(String numOfMembers) {
        this.numOfMembers = numOfMembers;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
