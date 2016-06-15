package android.hmkcode.com.myapplication123.Classes;

/**
 * Created by Ahmed Hamdy on 6/13/2016.
 */
public class Skill {

    private String category;
    private String skill;


    public Skill(String category, String skill){
        this.category = category;
        this.skill = skill;

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


}
