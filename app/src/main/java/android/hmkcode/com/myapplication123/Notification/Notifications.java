package android.hmkcode.com.myapplication123.Notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.hmkcode.com.myapplication123.Classes.Notification;
import android.hmkcode.com.myapplication123.Classes.Team;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.CreateTeam.DividerItemDecoration;
import android.hmkcode.com.myapplication123.CreateTeam.UserAdapter;
import android.hmkcode.com.myapplication123.DB.SqlLiteDataBaseHelper;
import android.hmkcode.com.myapplication123.MyTeams.MyTeams;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Notifications extends Fragment {

    private List<Notification> notificationsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    Fragment fragment;
    List<Notification> notifications;


    public Notifications() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_view_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_notification);
        mAdapter = new NotificationAdapter(notificationsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Notification notification = notificationsList.get(position);

                if (notification.getType().equalsIgnoreCase("invite")) {
                    fragment = new ViewNotification();

                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", notification.getTeamID());
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_notifications, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else if (notification.getType().equalsIgnoreCase("accept")) {
                    fragment = new ViewNotificationConfirm();

                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", notification.getTeamID());
                    bundle.putString("targetId", notification.getTargetUserId());
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_notifications, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                Toast.makeText(getContext(), notification.getType() + " is selected!" + notification.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);
        prepareNotifications();


    }

    public void onButtonPressed(Uri uri) {


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void prepareNotifications() {


//        Notification notificationview = new Notification();
//        Notification notification2 = null;
//
//        notification2 = Notification.notificationsGetData(getContext());
//        Log.i("HamdyJSON", myArray);



//            JSONArray myJSONArray = new JSONArray(myArray);
//            Log.i("HamdyJSON", myJSONArray.toString());
//            MyToast.toast(getContext(),myJSONArray + " ", 3);
//            for (int i = 0; i < myJSONArray.length(); i++){
//                JSONObject jsonObject = myJSONArray.getJSONObject(i);
//                String type = jsonObject.getString("type");
//                notification2.setType(type);
//                if (type.equalsIgnoreCase("accept"))
//                {
//                    String teamName = jsonObject.getString("teamName");
//                    notification2.setTeamName(teamName);
//                    String targetUserId =  jsonObject.getString("targetUserId");
//                    notification2.setTargetUserId(targetUserId);
//                }
//                String teamID = jsonObject.getString("teamID");
//                notification2.setTeamID(teamID);
//                String msg = jsonObject.getString("msg");
//                notification2.setMsg(msg);
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        notificationsList.add(notification2);
//        mAdapter.notifyDataSetChanged();


//        Notification notificationview = new Notification();
//        Notification notification2;
//        //notificationview = Notification.notificationsGetData(getContext());
//
//        if (notificationview.getType().equalsIgnoreCase("accept"))
//             notification2 = new Notification(notificationview.getId(), notificationview.getType(), notificationview.getMsg(),notificationview.getTeamID(),notificationview.getTargetUserId(),notificationview.getTeamName());
//        else
//             notification2 = new Notification(notificationview.getId(), notificationview.getType(), notificationview.getMsg(),notificationview.getTeamID());
//
//        notificationsList.add(notification2);
//

        SqlLiteDataBaseHelper helper = new SqlLiteDataBaseHelper(getContext());
        notifications = new ArrayList<>();
        notifications = helper.getAllNotifications();
        for (Notification notification : notifications)
        {
            notificationsList.add(notification);
        }

        mAdapter.notifyDataSetChanged();


    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Notifications.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Notifications.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_myteams, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.team_invite) {


        }

        return super.onOptionsItemSelected(item);
    }


}
