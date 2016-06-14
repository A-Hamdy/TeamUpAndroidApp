package android.hmkcode.com.myapplication123.CreateTeam;

import android.app.ProgressDialog;
import android.content.Context;
import android.hmkcode.com.myapplication123.Classes.Team;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.hmkcode.com.myapplication123.R;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CreateTeam extends Fragment {

    Team team = null;
    EditText  name, description, bio, duration;
    Button save;


    public CreateTeam() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_team, container, false);
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = (EditText) getView().findViewById(R.id.createTeamName);
        description = (EditText) getView().findViewById(R.id.createTeamDescription);
        bio = (EditText) getView().findViewById(R.id.createTeamBio);
        duration = (EditText) getView().findViewById(R.id.createTeamDuration);
        save = (Button) getView().findViewById(R.id.createTeamSaveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team = new Team();
                team.setTitle(name.getText().toString());
                team.setBio(bio.getText().toString());
                team.setDescription(description.getText().toString());
                team.setDurtion(Integer.parseInt(duration.getText().toString()));
                team.setImage("image2");
                team.setOwnerId(69);
                team.setSkillId(1);

                //new HttpAsyncTask2().execute(Utilites.URL_InviteUsers);
                new HttpAsyncTask().execute(Utilites.URL_CreateTeam);

            }
        });

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
            progressDialog.setMessage("Creating Team ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = null;
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

            MyToast.toast(getActivity().getApplicationContext(), result + " ");

            progressDialog.cancel();

            /*Go to Users Fragment*/
//            Fragment fragment = new TeamUsersList();
            Fragment fragment = new AddTeamSkills();

            Bundle dataId = new Bundle();
            dataId.putInt("ownerId",team.getOwnerId());
            dataId.putInt("skillId",team.getSkillId());
            fragment.setArguments(dataId);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.createTeam, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }


    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Send Users ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = "hh";
            try {

                JSONArray jsonArray = new JSONArray();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", 69);
                jsonArray.put(jsonObject);

                jsonObject = new JSONObject();
                jsonObject.put("id", 68);

                jsonArray.put(jsonObject);


                JSONObject userList = new JSONObject();
                userList.put("teamId", 282);
                userList.put("usersId", jsonArray);


                result = WebServiceHandler.handler(urls[0], userList);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            //MyToast.toast(getActivity().getApplicationContext(), result + " ");
            progressDialog.cancel();

        }
    }


}