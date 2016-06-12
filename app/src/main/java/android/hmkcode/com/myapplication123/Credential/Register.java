package android.hmkcode.com.myapplication123.Credential;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.MainActivity.MainActivity;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;


public class Register extends AppCompatActivity implements View.OnClickListener {


    User myUser;
    Button btn_continue;

    EditText et_name,
            et_email,
            et_password,
            et_passwordConfirm,
            et_linkedIn,
            et_bio,
            et_phone;

    ImageView image;
    Bitmap photo;
    Bitmap defaultPhoto;

    String name, email, password, confirmPassword, linkedin, bio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        defaultPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordConfirm = (EditText) findViewById(R.id.et_passwordConfirm);
        et_linkedIn = (EditText) findViewById(R.id.et_linkedIn);
        et_bio = (EditText) findViewById(R.id.et_bio);
        et_phone = (EditText) findViewById(R.id.et_phone);
        image = (ImageView) findViewById(R.id.image);

        btn_continue.setOnClickListener(this);
        image.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image:
                //getCamera();
                break;

            case R.id.btn_continue:
                if (validate()) {
                    new HttpAsyncTask().execute(Utilites.URL_Register);
                } else {
                    MyToast.toast(getApplicationContext(), "Failed To Register .:D");
                }
                break;
        }
    }

    void getCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1888);
        // CAMERA_REQUEST = 1888 we use request code cause onActivityResult may be used from different intents.
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (photo != null)
            finish();

    }


    public boolean validate() {
        boolean valid = true;

        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirmPassword = et_passwordConfirm.getText().toString();
        String linkedin = et_linkedIn.getText().toString();
        String bio = et_bio.getText().toString();
        String phone = et_phone.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            et_name.setError("at least 3 characters");
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Not valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError("Password length 4 - 10 characters");
            valid = false;
        } else {
            et_password.setError(null);
        }


        if (!validatePassword(password, confirmPassword)) {
            et_passwordConfirm.setError("Password not matched !");
            valid = false;
        } else {
            et_passwordConfirm.setError(null);
        }


        if (linkedin.isEmpty()) {
            et_linkedIn.setError("Required field");
            valid = false;
        } else {
            et_linkedIn.setError(null);
        }


        if (bio.isEmpty()) {
            et_bio.setError("Required field");
            valid = false;
        } else {
            et_bio.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 11) {
            et_phone.setError("phone number invalid (11 characters)");
            valid = false;
        } else {
            et_phone.setError(null);
        }

        if (valid) {
            myUser = new User();
            myUser.setName(name);
            myUser.setEmail(email);
            myUser.setPassword(password);
            myUser.setBio(bio);
            myUser.setLinkedIn(linkedin);
            myUser.setPhone(phone);
        }

        return valid;
    }


    boolean validatePassword(String pass1, String pass2) {
        boolean result = false;
        if (!pass1.isEmpty() && !pass2.isEmpty()) {
            if (pass1.equals(pass2))
                result = true;
        }
        return result;
    }

    boolean validateEmail(String email) {
        if (email.isEmpty())
            return false;
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



/* WEBSERVICE*/

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = null;

               // return POST(urls[0], myUser);
                try {

//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("name", myUser.getName());
//                    jsonObject.put("email", myUser.getEmail());
//                    jsonObject.put("password", myUser.getPassword());
//                    jsonObject.put("phone", myUser.getPhone());
//                    jsonObject.put("bio", myUser.getBio());
//                    jsonObject.put("linkedIn", myUser.getUrl());
//
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
//                JSONObject resultObject = new JSONObject(result);
//                Integer id = resultObject.getInt("id");

                Gson jsonBuilder = new Gson();
                User userData = jsonBuilder.fromJson(result,User.class);

                JSONObject resultObject = new JSONObject(result);
                Integer id = resultObject.getInt("id");

                MyToast.toast(getApplicationContext(), userData.getEmail() + " : Your ID :D " + id);

                if (id != null) {

                    myUser.userSaveData(getApplicationContext(),result);

                    Intent toIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(toIntent);
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

          progressDialog.cancel();

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toLogin) {
            Intent toLoginIntent = new Intent(this, LoginActivity.class);
            startActivity(toLoginIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
