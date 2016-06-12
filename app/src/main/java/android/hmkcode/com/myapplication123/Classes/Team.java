package android.hmkcode.com.myapplication123.Classes;

/**
 * Created by Ahmed Hamdy on 6/11/2016.
 */
public class Team {
    
    private String id;
    private String title;
    private String bio;
    private String description;
    private String teamImage;
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

    public String getTeamImage() {
        return teamImage;
    }

    public void setTeamImage(String teamImage) {
        this.teamImage = teamImage;
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




}
