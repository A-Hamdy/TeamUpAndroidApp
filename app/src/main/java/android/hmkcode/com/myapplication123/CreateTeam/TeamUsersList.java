package android.hmkcode.com.myapplication123.CreateTeam;

import android.app.ProgressDialog;
import android.content.Context;
import android.hmkcode.com.myapplication123.Classes.Team;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.hmkcode.com.myapplication123.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TeamUsersList extends Fragment {

    private List<User> users;
    private List<User> users2;

    List<User> myUsers;
    private RecyclerView rv;
    int ownerId, skillId;
    Team team = null;
    public TeamUsersList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ownerId = getArguments().getInt("ownerId");
        skillId = getArguments().getInt("skillId");

        team = new Team();
        team.setOwnerId(ownerId);
        team.setSkillId(skillId);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team_users_list, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = (RecyclerView) getView().findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        new HttpAsyncTask().execute(Utilites.URL_GetSuggestedUsers);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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

//            for (User user : myUsers) {
//                users2.add(new User(user.getName(), user.getEmail()));
////                users2.add(new User("Ahmed Hamdy 2", "ahmedhamdy2222@gmail.com"));
//
//                MyToast.toast(getActivity().getApplicationContext(), user.getEmail() + " ");
//            }


            initializeData();
            initializeAdapter();
            progressDialog.cancel();

        }
    }


    private void initializeData() {
        users = new ArrayList<>();
        users = myUsers;

    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(users);
        rv.setAdapter(adapter);
    }


}
