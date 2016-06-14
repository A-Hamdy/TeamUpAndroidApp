package android.hmkcode.com.myapplication123.CreateTeam;

import android.app.ProgressDialog;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    List<User> users;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        Button invite;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            invite = (Button) itemView.findViewById(R.id.inviteButton);


            invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyToast.toast(v.getContext(),"clicked"+ getAdapterPosition());

                }
            });
//            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }




    RVAdapter(List<User> users){
        this.users = users;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
        personViewHolder.personName.setText(users.get(i).getName());
        personViewHolder.personAge.setText(users.get(i).getEmail());
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.toast(v.getContext(),"View clicked" + ":P" + users.get(i).getName());
                new HttpAsyncTask2().execute(Utilites.URL_InviteUsers);

            }
        });
//        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(get());
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Send Users ...");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = "hh";
            try {

                /*Using Gson Library JSON*/
                JSONArray jsonArray = new JSONArray();

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id",2);

                JSONObject jsonObject2 = new JSONObject();
                jsonObject1.put("id",3);

                jsonArray.put(jsonObject1);
                jsonArray.put(jsonObject2);


                JSONObject userList = new JSONObject();
                userList.put("teamId",50);
                userList.put("usersId",jsonArray);

//                MyToast.toast(getActivity(),userList.toString());



                result = WebServiceHandler.handler(urls[0], userList);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

//            MyToast.toast(getActivity().getApplicationContext(), result + " ");

//            Gson jsonBuilder = new Gson();
//            Type myList = new TypeToken<ArrayList<User>>(){}.getType();
//            myUsers = new ArrayList<>();
//            myUsers = jsonBuilder.fromJson(result,myList);
//
////            for (User user : myUsers) {
////                users2.add(new User(user.getName(), user.getEmail()));
//////                users2.add(new User("Ahmed Hamdy 2", "ahmedhamdy2222@gmail.com"));
////
////                MyToast.toast(getActivity().getApplicationContext(), user.getEmail() + " ");
////            }
//
//
//            initializeData();
//            initializeAdapter();
//            progressDialog.cancel();

        }


    }

}
