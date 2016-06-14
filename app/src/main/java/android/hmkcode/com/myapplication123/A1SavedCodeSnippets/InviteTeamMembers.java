package android.hmkcode.com.myapplication123.A1SavedCodeSnippets;

/**
 * Created by Ahmed Hamdy on 6/14/2016.
 */
public class InviteTeamMembers {



/*
    URL = new HttpAsyncTask2().execute(Utilites.URL_InviteUsers);

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getApplicationContext());
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

*/

}
