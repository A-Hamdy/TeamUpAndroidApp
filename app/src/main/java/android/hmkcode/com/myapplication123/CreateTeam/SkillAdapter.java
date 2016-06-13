package android.hmkcode.com.myapplication123.CreateTeam;


import android.hmkcode.com.myapplication123.Classes.Skill;
import android.hmkcode.com.myapplication123.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.MyViewHolder> {

    private List<Skill> skillsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category, skill, ratingDescription;
        public RatingBar stars;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.category);
            ratingDescription = (TextView) view.findViewById(R.id.rateDescription);
            skill = (TextView) view.findViewById(R.id.skill);
            stars = (RatingBar) view.findViewById(R.id.skillStars);

        }
    }


    public SkillAdapter(List<Skill> skillsList) {
        this.skillsList = skillsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_row_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Skill skill = skillsList.get(position);
        holder.category.setText(skill.getCategory());
        holder.ratingDescription.setText(skill.getRatingDescription());
        holder.skill.setText(skill.getSkill());
        String rating = (String) holder.ratingDescription.getText();
        if (rating.equalsIgnoreCase("poor"))
            holder.stars.setRating(1);
        else if (rating.equalsIgnoreCase("good"))
            holder.stars.setRating(2);
        else if (rating.equalsIgnoreCase("excellent"))
            holder.stars.setRating(3);
    }

    @Override
    public int getItemCount() {
        return skillsList.size();
    }
}