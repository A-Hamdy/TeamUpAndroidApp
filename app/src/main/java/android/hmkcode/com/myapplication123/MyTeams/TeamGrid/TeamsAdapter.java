package android.hmkcode.com.myapplication123.MyTeams.TeamGrid;

import android.content.Context;
import android.hmkcode.com.myapplication123.R;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;


public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> {

    private Context mContext;
    private List<TeamGrid> teamsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, teamID;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            teamID = (TextView) view.findViewById(R.id.teamId);
        }
    }


    public TeamsAdapter(Context mContext, List<TeamGrid> teamsList) {
        this.mContext = mContext;
        this.teamsList = teamsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TeamGrid team = teamsList.get(position);
        holder.title.setText(team.getName());
        holder.count.setText(team.getNumOfMembers() + " Team Member");
        holder.teamID.setText(team.getTeamId());

        if (team.isOwner())
            holder.overflow.setImageResource(R.drawable.owner_star);

        Glide.with(mContext).load(team.getThumbnail()).into(holder.thumbnail);
//        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "thumb " + holder.title.getText() + " ID: " + team.getTeamId(), Toast.LENGTH_SHORT).show();
//            }
//        });


//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//                //Toast.makeText(mContext, "current " + holder.title.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

     /*
    // Showing popup menu when tapping on 3 dots

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


    // * Click listener for popup menu items

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "View", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    class MyImageView implements ImageView.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "View Clicked", Toast.LENGTH_SHORT).show();
        }
    }
*/
    @Override
    public int getItemCount() {
        return teamsList.size();
    }
}
