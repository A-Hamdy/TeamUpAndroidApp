package android.hmkcode.com.myapplication123;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import Classes.User;
import Classes.Utilites;
import Classes.WebServiceHandler;

public class LoginActivity extends AppCompatActivity {
    Button Login;

    EditText Email;

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
//                    new HttpAsyncTask().execute(Utilites.URL_Login);
                    new HttpAsyncTask().execute(Utilites.URL_CreateTeam);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials !!", Toast.LENGTH_SHORT).show();
                }

                /* TEST Condition*/
                if (Email.getText().toString().equals("a@a.com"))
                {
                    Intent toIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(toIntent);
                    finish();
                }


            }
        });


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


        if (valid)
        {
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

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.albumme);
            String imgEncode = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG,100);
            try {

                /*HardCoded JSON*/
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", "title");
                jsonObject.put("bio","bio" );
                jsonObject.put("description","description");
                jsonObject.put("durtion","5");
                jsonObject.put("ownerId","2");
                jsonObject.put("skillId","4");
                jsonObject.put("image",imgEncode);




                /*Using Gson Library JSON*/
//                Gson jsonBuilder = new Gson();
//                JSONObject jsonObject = new JSONObject(jsonBuilder.toJson(myUser));

                result = WebServiceHandler.handler(urls[0],jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
//            try {
//                URL url = new URL("http://10.144.3.172:40663/TeamUP/rest/WebService/login");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setDoInput (true);
//                urlConnection.setDoOutput (true);
//                urlConnection.setUseCaches (false);
//                urlConnection.setRequestProperty("Content-Type","application/json");
////                urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
//
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("email", "adel@yahoo.com");
//                jsonParam.put("password", "123");
//
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//                printout = new DataOutputStream(urlConnection.getOutputStream ());
//                printout.write(Integer.parseInt(URLEncoder.encode(jsonParam.toString(),"UTF-8")));
//                printout.flush ();
//                printout.close ();
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);
//                }
//
//            }catch( Exception e) {
//                e.printStackTrace();
//            }
//            finally {
//                urlConnection.disconnect();
//
//            }
//
//
//            return result.toString();
//        }

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {



            try {
                Toast.makeText(getApplicationContext(),result + " ",Toast.LENGTH_LONG).show();
                JSONObject resultObject = new JSONObject(result);

                MyToast.toast(getApplicationContext(), result);

                Integer id = resultObject.getInt("id");
                MyToast.toast(getApplicationContext(), id + " : Your ID From Login :D");

//                MyToast.toast(getApplicationContext(), "bytes : " + resultObject.getString("profilePictureBase64"));
                /*
                 String data =  resultObject.getString("profilePictureBase64");
                 byte[] myimg = data.getBytes();
                 Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
                 img.setImageBitmap(decodedByte);*/
                if (id != null)
                {
/*
                    String data = resultObject.getString("profilePictureBase64");
                    byte[] myimg = Base64.decode(data,Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
                    MyToast.toast(getApplicationContext(), "bytes : " + resultObject.getString("profilePictureBase64"));
                    img.setImageBitmap(decodedByte);
*/

                    //myUser.userSaveData(getApplicationContext(),result);
//
// Read Stuff
// myUser = myUser.userGetData(getApplicationContext());


//                    String data = myUser.getProfilePictureBase64();
//                    byte[] myimg = Base64.decode(data,Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
//
//                    myUser.imageSaveToInternal(getApplicationContext(),decodedByte,"userImage");
//
                    // Read Stuff
//                    Intent toIntent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(toIntent);
//                    finish();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.cancel();

        }
    }





    /* WEBSERVICE*/
//
//    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Please Wait ...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            if (myUser != null)
//                return POST(urls[0], myUser);
//            else
//                return "error myUser !";
//        }
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                JSONObject resultObject = new JSONObject(result);
//                Integer id = resultObject.getInt("id");
//                MyToast.toast(getApplicationContext(), id + " : Your ID From Login :D");
////                MyToast.toast(getApplicationContext(), "bytes : " + resultObject.getString("profilePictureBase64"));
//                if (id != null) {
//                    Intent toIntent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(toIntent);
//                    finish();
//
////                    String data = resultObject.getString("profilePictureBase64");
////                    byte[] myimg = Base64.decode(data,Base64.DEFAULT);
////                    Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
//
////                    String data =  resultObject.getString("profilePictureBase64");
////                    byte[] myimg = data.getBytes();
////                    Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
////                    img.setImageBitmap(decodedByte);
//
//
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            progressDialog.cancel();
//
//        }
//    }

    /*Method post*/
//    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String line = "";
//        String result = "";
//        while ((line = bufferedReader.readLine()) != null)
//            result += line;
//
//        inputStream.close();
//        return result;
//
//    }
//
//    public static String POST(String url, User user) {
//        InputStream inputStream = null;
//        String result = "";
//        try {
//
//            // 1. create HttpClient
//            HttpClient httpclient = new DefaultHttpClient();
//
//            // 2. make POST request to the given URL
//            HttpPost httpPost = new HttpPost(url);
//
//            String json = "";
//
//            // 3. build jsonObject
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("email", user.getEmail());
//            jsonObject.accumulate("password", user.getPassword());
//
//            // 4. convert JSONObject to JSON to String
//            json = jsonObject.toString();
//
//            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
//            // ObjectMapper mapper = new ObjectMapper();
//            // json = mapper.writeValueAsString(person);
//
//            // 5. set json to StringEntity
//            StringEntity se = new StringEntity(json);
//
//            // 6. set httpPost Entity
//            httpPost.setEntity(se);
//
//            // 7. Set some headers to inform server about the type of the content
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            // 8. Execute POST request to the given URL
//            HttpResponse httpResponse = httpclient.execute(httpPost);
//
//            // 9. receive response as inputStream
//            inputStream = httpResponse.getEntity().getContent();
//
//            // 10. convert inputstream to string
//            if (inputStream != null)
//                result = convertInputStreamToString(inputStream);
//            else
//                result = "Did not work!";
//
//        } catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
//        }
//
//        // 11. return result
//        return result;
//    }


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
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

//    public static Bitmap decodeBase64(String input)
//    {
//        byte[] decodedBytes = Base64.decode(input, 0);
//        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//    }
}
