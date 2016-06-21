package android.hmkcode.com.myapplication123.AddNewSkill;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hmkcode.com.myapplication123.Classes.CategorySkills;
import android.hmkcode.com.myapplication123.Classes.Skill;
import android.hmkcode.com.myapplication123.Classes.SkillInCategory;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.CreateTeam.DividerItemDecoration;
import android.hmkcode.com.myapplication123.CreateTeam.SkillAdapter;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//skills_tab_fragment

public class AddNewSkillFragment extends Fragment {

    private List<Skill> skillList = new ArrayList<>();



    Spinner spinner_category, spinner_skill;
    ArrayList<CategorySkills> categorySkillsArrayList;
    User myUser;

    public AddNewSkillFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myUser = new User();
        myUser = myUser.userGetData(getContext());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);



        View v2 = inflater.inflate(R.layout.alertdialog_new_skill, container, false);


        spinner_category = (Spinner) v2.findViewById(R.id.catspinner);


//        new HttpAsyncTask().execute(Utilites.URL_AddNewSkill);
        new HttpAsyncTask().execute(Utilites.URL_Logout);

        return inflater.inflate(R.layout.skills_tab_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);







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



    public class HttpAsyncTasks extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting Skills ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = WebServiceHandler.handler(urls[0], "get");
            return result;

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

//            MyToast.toast(getContext(),result + "  ");

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject categoryObject = jsonArray.getJSONObject(i);
                    CategorySkills category = new CategorySkills();
                    category.setId(categoryObject.getInt("id"));
                    category.setName(categoryObject.getString("name"));
                    JSONArray skillsArray = categoryObject.getJSONArray("skill");

                    ArrayList<SkillInCategory> skills = new ArrayList<>();
                    for (int j = 0; j < skillsArray.length(); j++) {
                        JSONObject skillObject = skillsArray.getJSONObject(j);
                        SkillInCategory skillInCategory = new SkillInCategory();
                        skillInCategory.setId(skillObject.getInt("id"));
                        skillInCategory.setName(skillObject.getString("name"));
                        skills.add(skillInCategory);

                    }

                    category.setSkills(skills);

                    categorySkillsArrayList.add(category);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.cancel();

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add_skill, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_skill) {
            viewAlertDialog();
        }
        if (id == R.id.done_add_skill) {
           // startToCreateTeam();

        }
        return super.onOptionsItemSelected(item);
    }




    public void viewAlertDialog() {

        ArrayList<String> categoryName = new ArrayList<>();
        final ArrayList<String> skillName = new ArrayList<>();

        for (CategorySkills categorySkills : categorySkillsArrayList) {
            categoryName.add(categorySkills.getName());

        }

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.alertdialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(promptsView);


        final Spinner categorySpinner = (Spinner) promptsView.findViewById(R.id.catspinner);


        ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryName);


        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        categorySpinner.setAdapter(adp);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                MyToast.toast(getContext(), name + " : name");
                skillName.clear();
                for (CategorySkills categorySkills : categorySkillsArrayList) {
                    if (categorySkills.getName().equals(name)) {
                        for (SkillInCategory skillInCategory : categorySkills.getSkills()) {
                            skillName.add(skillInCategory.getName());
                        }
                    }




                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setTitle("Choose Skill");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String categoryName = (String) categorySpinner.getSelectedItem();



            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);


    }




    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Save Data ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = null;


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userId",myUser.getId());
                jsonObject.put("categoryId","1");
                jsonObject.put("skillName","ADEL");

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

        }
    }

//
//    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Save Data ...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//
//
//            String result = null;
//
//
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("userId",myUser.getId());
//
//
//                result = WebServiceHandler.handler(urls[0], jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            return result;
//
//        }
//
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//
//            MyToast.toast(getActivity().getApplicationContext(), result + " ");
//
//            progressDialog.cancel();
//
//        }
//    }



}
