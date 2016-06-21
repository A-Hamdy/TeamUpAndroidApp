package android.hmkcode.com.myapplication123.Notification;


import android.hmkcode.com.myapplication123.Classes.Notification;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<Notification> notificationsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type, msg, id, targetUserId;
        public ImageView notificationImage;

        public MyViewHolder(View view) {
            super(view);

            type = (TextView) view.findViewById(R.id.notification_type);
            msg = (TextView) view.findViewById(R.id.notification_msg);
            notificationImage = (ImageView) view.findViewById(R.id.notification_image);
            id = (TextView) view.findViewById(R.id.notification_id);
            targetUserId = (TextView) view.findViewById(R.id.target_user_id);

        }
    }


    public NotificationAdapter(List<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.type.setText(notification.getType());
        holder.msg.setText(notification.getMsg());
        holder.id.setText(notification.getId());
        holder.targetUserId.setText(notification.getTargetUserId());
        holder.notificationImage.setImageResource(R.drawable.skillicon_it);



    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
}