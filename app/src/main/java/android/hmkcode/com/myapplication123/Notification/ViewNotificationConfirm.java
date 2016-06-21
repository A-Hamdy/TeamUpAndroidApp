package android.hmkcode.com.myapplication123.Notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;


public class ViewNotificationConfirm extends Fragment {

    Team team = null;
    EditText name, description, bio, duration;
    Bitmap photo;
    Bitmap photoDefault;
    String photoDefaultBytes;
    ImageView teamImage;
    Fragment fragment;
    String imgEncode;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Button acceptConfirm, rejectConfirm;
    Boolean responseConfirm;
    User myUser;
    String teamID, targetID;


    public ViewNotificationConfirm() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        myUser = new User();
        myUser = myUser.userGetData(getContext());

        Bundle bundle = getArguments();
        teamID = bundle.getString("teamId");
        targetID = bundle.getString("targetId");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_confirm_notification, container, false);
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        acceptConfirm = (Button) getView().findViewById(R.id.accept);
        rejectConfirm = (Button) getView().findViewById(R.id.reject);


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


//        name = (EditText) getView().findViewById(R.id.createTeamName);
//        description = (EditText) getView().findViewById(R.id.createTeamDescription);
//        bio = (EditText) getView().findViewById(R.id.createTeamBio);
//        duration = (EditText) getView().findViewById(R.id.createTeamDuration);
//        teamImage = (ImageView) getView().findViewById(R.id.team_image);

//        teamImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getCamera();
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//            }
//        });
//
//        photoDefault = BitmapFactory.decodeResource(getResources(),R.drawable.albumme);
//        photoDefaultBytes = encodeToBase64(photoDefault, Bitmap.CompressFormat.PNG,100);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();

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
        if (id == R.id.done_team_info) {

        }

        return super.onOptionsItemSelected(item);
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

            MyToast.toast(getContext(), "done : " + result);
            progressDialog.cancel();


        }


    }


}