package android.hmkcode.com.myapplication123.CreateTeam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hmkcode.com.myapplication123.Classes.CategorySkills;
import android.hmkcode.com.myapplication123.Classes.Skill;
import android.hmkcode.com.myapplication123.Classes.SkillInCategory;
import android.hmkcode.com.myapplication123.Classes.Team;
import android.hmkcode.com.myapplication123.Classes.User;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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


public class ViewSuggestedUsers extends Fragment {

    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    Fragment fragment;
    Team team;
    List<User> myUsers;
    List<Integer> usersIds;

    public ViewSuggestedUsers() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        team = new Team();
        team = team.teamGetData(getContext());
        usersIds = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);



        View v = inflater.inflate(R.layout.fragment_viewsuggested_users, container, false);



        return inflater.inflate(R.layout.fragment_viewsuggested_users, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_user);

        new HttpAsyncTask().execute(Utilites.URL_GetSuggestedUsers);

        mAdapter = new UserAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                User user = userList.get(position);
                Toast.makeText(getContext(), user.getName() + " is selected!" + user.getId(), Toast.LENGTH_SHORT).show();

                TextView textView = (TextView) view.findViewById(R.id.user_checked);
                String checked = textView.getText().toString();
                if (checked.equalsIgnoreCase("checked")){
                    usersIds.remove(Integer.valueOf(Integer.parseInt(user.getId())));
                    ImageView image = (ImageView) view.findViewById(R.id.user_image);
                    image.setImageResource(R.drawable.user_willbeadded);
                    textView.setText("notChecked");

                }else{
                    textView.setText("checked");
                    usersIds.add(Integer.parseInt(user.getId()));
                    ImageView image = (ImageView) view.findViewById(R.id.user_image);
                    image.setImageResource(R.drawable.user_added);
                }


                MyToast.toast(getContext(), usersIds + " : IDS");



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);




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


    private void prepareUserData(String userId, String userName, String userEmail) {
        User user = new User(userId, userName, userEmail);
        userList.add(user);


    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ViewSuggestedUsers.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ViewSuggestedUsers.ClickListener clickListener) {
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

        inflater.inflate(R.menu.menu_team_invite, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.team_invite) {
              new HttpAsyncTask2().execute(Utilites.URL_InviteUsers);

        }

        return super.onOptionsItemSelected(item);
    }






    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting Users ...");
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... urls) {


            String result = "hh";
            try {

                /*Using Gson Library JSON*/
                Gson jsonBuilder = new Gson();
                JSONObject jsonObject = new JSONObject(jsonBuilder.toJson(team));
                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //MyToast.toast(getActivity().getApplicationContext(), result + " ");

            Gson jsonBuilder = new Gson();
            Type myList = new TypeToken<ArrayList<User>>(){}.getType();
            myUsers = new ArrayList<>();
            myUsers = jsonBuilder.fromJson(result,myList);

            for (User user : myUsers) {
                prepareUserData(user.getId(),user.getName(), user.getEmail());
            }

            mAdapter.notifyDataSetChanged();

//            new HttpAsyncTask2().execute(Utilites.URL_InviteUsers);
            progressDialog.cancel();
//            progressDialog.dismiss();



        }
    }



 //invite
    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Send Invitation Users ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = "hh";
            try {

                /*Using Gson Library JSON*/
                JSONArray jsonArray = new JSONArray();

                for (Integer id : usersIds)
                {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",id);
                    jsonArray.put(jsonObject);
                }



                JSONObject userLists = new JSONObject();
                userLists.put("teamId",team.getId());
                userLists.put("usersId",jsonArray);


                result = WebServiceHandler.handler(urls[0], userLists);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            MyToast.toast(getContext(),"sent Notification");
            progressDialog.cancel();

        }


    }



}
