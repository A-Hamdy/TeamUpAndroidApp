package android.hmkcode.com.myapplication123.MyTeams.TeamGrid;

/**
 * Created by Lincoln on 18/05/16.
 */
public class TeamGrid {
    private String name;
    private int numOfMembers;
    private int thumbnail;

    public TeamGrid() {
    }

    public TeamGrid(String name, int numOfMembers, int thumbnail) {
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(int numOfMembers) {
        this.numOfMembers = numOfMembers;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
