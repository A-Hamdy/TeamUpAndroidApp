package android.hmkcode.com.myapplication123.Classes;

/**
 * Created by Ahmed Hamdy on 6/13/2016.
 */
public class Skill {

    private String category;
    private String skill;
    private int rating;
    private String ratingDescription;

    public Skill(String category, String skill, String ratingDescription){
        this.category = category;
        this.skill = skill;
        this.ratingDescription = ratingDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }




}
