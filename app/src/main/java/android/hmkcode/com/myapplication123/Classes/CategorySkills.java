package android.hmkcode.com.myapplication123.Classes;

import java.util.ArrayList;

/**
 * Created by Ahmed Hamdy on 6/16/2016.
 */
public class CategorySkills {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<SkillInCategory> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<SkillInCategory> skills) {
        this.skills = skills;
    }


    private int id;
    private String name;
    private ArrayList<SkillInCategory> skills;


}
