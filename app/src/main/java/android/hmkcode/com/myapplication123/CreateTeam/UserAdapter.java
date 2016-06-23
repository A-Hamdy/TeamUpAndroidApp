package android.hmkcode.com.myapplication123.CreateTeam;


import android.hmkcode.com.myapplication123.Classes.Skill;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> usersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userEmail;
        public ImageView userImage;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.user_name);
            userEmail = (TextView) view.findViewById(R.id.user_email);
            userImage = (ImageView) view.findViewById(R.id.user_image);

        }
    }


    public UserAdapter(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.userName.setText(user.getName());
        holder.userEmail.setText(user.getEmail());
        holder.userImage.setImageResource(R.drawable.user_willbeadded);


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}