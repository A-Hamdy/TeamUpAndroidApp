package android.hmkcode.com.myapplication123.CreateTeam;


import android.hmkcode.com.myapplication123.Classes.Skill;
import android.hmkcode.com.myapplication123.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.MyViewHolder> {

    private List<Skill> skillsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category, skill;
        public ImageView skillImage;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.category);
             skill = (TextView) view.findViewById(R.id.skill);
            skillImage = (ImageView) view.findViewById(R.id.skill_image);

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
        holder.skill.setText(skill.getSkill());

        String myCategory = skill.getCategory();
        if (myCategory.equalsIgnoreCase("IT"))
            holder.skillImage.setImageResource(R.drawable.skillicon_it);
        else if (myCategory.equalsIgnoreCase("Game Development"))
            holder.skillImage.setImageResource(R.drawable.skillicon_game);
        else if (myCategory.equalsIgnoreCase("Design"))
            holder.skillImage.setImageResource(R.drawable.skillicon_design);
        else
            holder.skillImage.setImageResource(R.drawable.skillicon_it);



    }

    @Override
    public int getItemCount() {
        return skillsList.size();
    }
}