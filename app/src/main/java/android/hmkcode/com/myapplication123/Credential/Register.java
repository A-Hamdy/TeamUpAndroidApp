package android.hmkcode.com.myapplication123.Credential;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.MainActivity.MainActivity;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Register extends AppCompatActivity implements View.OnClickListener {

    GoogleCloudMessaging gcm;
    String regId;
    User myUser;
    Button btn_continue;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Integer id;

    EditText et_name,
            et_email,
            et_password,
            et_passwordConfirm,
            et_linkedIn,
            et_bio,
            et_phone;

    ImageView image;
    Bitmap photo , photoDefault;
    String imgEncode , photoDefaultBytes;
    Bitmap defaultPhoto;
    TextView loginback;

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
        loginback = (TextView) findViewById(R.id.textView9);
        loginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(toLoginIntent);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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

//    void getCamera() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, 1888);
//        // CAMERA_REQUEST = 1888 we use request code cause onActivityResult may be used from different intents.
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1888 && resultCode == RESULT_OK) {
//            photo = (Bitmap) data.getExtras().get("data");
//            image.setImageBitmap(photo);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor =getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.image);
                photo = BitmapFactory.decodeFile(imgDecodableString);
                imgView.setImageBitmap(photo);
                imgEncode = encodeToBase64(photo, Bitmap.CompressFormat.PNG, 100);
            } else {
                Toast.makeText(getApplicationContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
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
            if (imgEncode == null) {
                photoDefault = BitmapFactory.decodeResource(getResources(),R.drawable.defaultimg);
                photoDefaultBytes = encodeToBase64(photoDefault, Bitmap.CompressFormat.JPEG,10);
                myUser.setProfilePictureBase64(photoDefaultBytes);
            }
            else
                myUser.setProfilePictureBase64(imgEncode);


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

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", myUser.getName());
                    jsonObject.put("email", myUser.getEmail());
                    jsonObject.put("password", myUser.getPassword());
                    jsonObject.put("phone", myUser.getPhone());
                    jsonObject.put("bio", myUser.getBio());
                    jsonObject.put("linkedIn", myUser.getLinkedIn());
                    jsonObject.put("image", myUser.getProfilePictureBase64());

//
//                    Gson jsonBuilder = new Gson();
//                    JSONObject jsonObject = new JSONObject(jsonBuilder.toJson(myUser));
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
                id = resultObject.getInt("id");

                MyToast.toast(getApplicationContext(), userData.getEmail() + " : Your ID :D " + id);

                if (id != null) {

                    myUser.userSaveData(getApplicationContext(),result);
                    sendToken();

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





    public void sendToken() {
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


    public void SendToServer() {

        new SendTokenToServer().execute(Utilites.URL_Notification_URL);
    }


    private class SendTokenToServer extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", id);
                jsonObject.put("token", regId);
                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            MyToast.toast(getApplicationContext(), "sent Token");
            Intent toIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(toIntent);
            finish();

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}


