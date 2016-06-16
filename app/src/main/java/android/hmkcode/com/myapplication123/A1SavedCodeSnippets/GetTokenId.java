package android.hmkcode.com.myapplication123.A1SavedCodeSnippets;

/**
 * Created by Ahmed Hamdy on 6/14/2016.
 */
public class GetTokenId {

/*

    URL = new HttpAsyncTask3().execute(Utilites.URL_Notification_URL);


    GoogleCloudMessaging gcm;
    String regId;

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

        //new HttpAsyncTask3().execute(Utilites.URL_Notification_URL);
    }


    private class HttpAsyncTask3 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Send To Server ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = null;
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", myUser.getId());
                jsonObject.put("token", regId);
                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            MyToast.toast(getApplicationContext(), "server data :" + result);
            progressDialog.cancel();


        }
    }
*/

}
