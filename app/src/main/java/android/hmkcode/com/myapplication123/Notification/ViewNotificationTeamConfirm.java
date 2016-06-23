package android.hmkcode.com.myapplication123.Notification;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.DB.SqlLiteDataBaseHelper;
import android.hmkcode.com.myapplication123.MyTeams.MyTeams;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewNotificationTeamConfirm extends Fragment {

    TextView team_name, team_desc, team_member_num, team_duration;
    ImageView team_image;
    Bitmap decodedByte;

    Button accept, reject;
    Boolean response;

    Button acceptConfirm, rejectConfirm;
    Boolean responseConfirm;
    User myUser;
    String teamID, targetID;
    SqlLiteDataBaseHelper helper;
    String notificationID;

    public ViewNotificationTeamConfirm() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();

        helper = new SqlLiteDataBaseHelper(getContext());
        myUser = new User();
        myUser = myUser.userGetData(getContext());

        teamID = bundle.getString("teamId");
        notificationID = bundle.getString("notificationID");
        targetID = bundle.getString("targetId");


        new HttpAsyncTasks().execute(Utilites.URL_GetTeamById);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.view_team_confirm, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        team_name = (TextView) getView().findViewById(R.id.team_name);
        team_desc = (TextView) getView().findViewById(R.id.team_desc);
        team_member_num = (TextView) getView().findViewById(R.id.team_member_num);
        team_duration = (TextView) getView().findViewById(R.id.team_duration);
        team_image = (ImageView) getView().findViewById(R.id.team_image);


        acceptConfirm = (Button) getView().findViewById(R.id.acceptButtton);
        rejectConfirm = (Button) getView().findViewById(R.id.rejectButton);


        acceptConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseConfirm = true;
                new HttpAsyncTask2().execute(Utilites.URL_ConfirmInvitation);
            }
        });

        rejectConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseConfirm = false;
                new HttpAsyncTask2().execute(Utilites.URL_ConfirmInvitation);
            }
        });

    }


    //
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class HttpAsyncTasks extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting Team Info ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", teamID);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String result = WebServiceHandler.handler(urls[0], jsonObject);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {


            try {


                JSONObject team = new JSONObject(result);

                String teamId = team.getString("id");
                String teamName = team.getString("title");
                String teamDesc = team.getString("description");
                String teamDuration = team.getString("durationDays");
                String teamImage = team.getString("image");

                JSONArray membersArrays = team.getJSONArray("userHasTeams");
                String teamMemberNum = membersArrays.length() + "";


                team_name.setText(teamName);
                team_desc.setText(teamDesc);
                team_member_num.setText(teamMemberNum);
                team_duration.setText(teamDuration);


                byte[] myimg = Base64.decode(teamImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);

                team_image.setImageBitmap(decodedByte);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.cancel();

        }
    }


    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Sending Response ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = "";
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ownerId", myUser.getId());
                jsonObject.put("teamId", teamID);
                jsonObject.put("userId", targetID);
                jsonObject.put("confirm",responseConfirm);


                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            progressDialog.cancel();
            helper.updateNotification(notificationID);

            Fragment fragment = new MyTeams();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.team_confirm, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }


    }

}
