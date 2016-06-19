package android.hmkcode.com.myapplication123.Credential;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.MainActivity.MainActivity;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.R;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;

public class LoginActivity extends AppCompatActivity {
    Button Login;

    EditText Email;

    GoogleCloudMessaging gcm;
    String regId;

    EditText pssword;
    ImageView img;
    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Login = (Button) findViewById(R.id.btn_login);
        Email = (EditText) findViewById(R.id.input_email);
        pssword = (EditText) findViewById(R.id.input_password);
        // img = (ImageView) findViewById(R.id.testImgView);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    new HttpAsyncTask().execute(Utilites.URL_Login);
//                    new HttpAsyncTask().execute(Utilites.URL_CreateTeam);
//                    new HttpAsyncTask().execute(Utilites.URL_GetSuggestedUsers);

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials !!", Toast.LENGTH_SHORT).show();
                }

                /* TEST Condition*/
                if (Email.getText().toString().equals("a@a.com")) {
                    Intent toIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(toIntent);
                    finish();
                }


            }
        });


    }


    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null);
    }


    public boolean validate() {
        boolean valid = true;

        String email = Email.getText().toString();
        String password = pssword.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Not valid email address");
            valid = false;
        } else {
            Email.setError(null);
        }


        if (valid) {
            myUser = new User();
            myUser.setEmail(email);
            myUser.setPassword(password);
        }

        return valid;
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = null;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.albumme);
            String imgEncode = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);
            //myUser.setProfilePictureBase64(imgEncode);
            try {
                /*Using Gson Library JSON*/
                Gson jsonBuilder = new Gson();
                JSONObject jsonObject = new JSONObject(jsonBuilder.toJson(myUser));

                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {
               // Toast.makeText(getApplicationContext(),result + " ",Toast.LENGTH_LONG).show();
                JSONObject resultObject = new JSONObject(result);
                Integer id = resultObject.getInt("id");
                MyToast.toast(getApplicationContext(), id + " : Your ID From Login :D");

                if (id != null) {


                    myUser.userSaveData(getApplicationContext(),result);

// Read Stuff
                    myUser = myUser.userGetData(getApplicationContext());

//                    String data = myUser.getProfilePictureBase64();
//                    byte[] myimg = Base64.decode(data,Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
//
//                    myUser.imageSaveToInternal(getApplicationContext(),decodedByte,"userImage");

                    // Read Stuff
                    Intent toIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(toIntent);
                    finish();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendToken();
            progressDialog.cancel();

        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toRegister) {
            Intent toRegisterIntent = new Intent(this, Register.class);
            startActivity(toRegisterIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /* Encode and Decode */
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

//    public static Bitmap decodeBase64(String input)
//    {
//        byte[] decodedBytes = Base64.decode(input, 0);
//        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//    }


    public void sendToken(){
        regId = registerGCM();
    }

    public String registerGCM() {
        String regId;
        gcm = GoogleCloudMessaging.getInstance(this);
        registerInBackground();

        return null;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";

                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regId = gcm.register(Utilites.GOOGLE_PROJECT_ID);
                    msg = "Device registered, registration ID=" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }

                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();
                SendToServer();

            }
        }.execute(null, null, null);
    }


    public void SendToServer(){

        new SendTokenToServer().execute(Utilites.URL_Notification_URL);
    }


    private class SendTokenToServer extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Send Token To Server ...");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", myUser.getId());
                jsonObject.put("token",  regId);
                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            MyToast.toast(getApplicationContext(),"sent Token");
//            progressDialog.dismiss();


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}




