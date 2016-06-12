package android.hmkcode.com.myapplication123.CreateTeam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.Classes.Team;
import android.hmkcode.com.myapplication123.MainActivity.MainActivity;
import android.hmkcode.com.myapplication123.ProjectUtitlites.MyToast;
import android.hmkcode.com.myapplication123.ProjectUtitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.hmkcode.com.myapplication123.R;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


public class CreateTeam extends Fragment {

    Team team = null;

    public CreateTeam() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        team = new Team();
        team.setTitle("teamTitle");
        team.setBio("bio");
        team.setDescription("descr");
        team.setDurtion(15);
        team.setImage("image");
        team.setOwnerId(69);
        team.setSkillId(1);

        new HttpAsyncTask().execute(Utilites.URL_CreateTeam);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_team, container, false);
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
            Fragment fragment = new TeamUsersList();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.createTeam, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

}