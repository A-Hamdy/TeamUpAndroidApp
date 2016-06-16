package android.hmkcode.com.myapplication123.CreateTeam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hmkcode.com.myapplication123.Classes.CategorySkills;
import android.hmkcode.com.myapplication123.Classes.Skill;
import android.hmkcode.com.myapplication123.Classes.SkillInCategory;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.hmkcode.com.myapplication123.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class AddTeamSkills extends Fragment {

    private List<Skill> skillList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SkillAdapter mAdapter;
    Fragment fragment;
    Spinner spinner_category, spinner_skill;
    ArrayList<CategorySkills> categorySkillsArrayList;
    public AddTeamSkills() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        categorySkillsArrayList = new ArrayList<>();

        View v = inflater.inflate(R.layout.fragment_add_team_skills, container, false);
        View v2 = inflater.inflate(R.layout.alertdialog_view, container, false);


        spinner_category = (Spinner) v2.findViewById(R.id.catspinner);
        spinner_skill = (Spinner) v2.findViewById(R.id.skillspinner);


        return inflater.inflate(R.layout.fragment_add_team_skills, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_skill);

        new HttpAsyncTasks().execute(Utilites.URL_GetAllCategories);

        mAdapter = new SkillAdapter(skillList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Skill skill = skillList.get(position);
                Toast.makeText(getContext(), skill.getCategory() + " is selected!", Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);

        prepareMovieData();


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


    private void prepareMovieData() {
//        Skill skill = new Skill("Computer Science", "Information Techonloy Skill", "Poor");
//        skillList.add(skill);
//
//        skill = new Skill("Computer Tech", "Information  Skill", "Good");
//        skillList.add(skill);
//
//        skill = new Skill("Science", "Techonloy Skill", "Excellent");
//        skillList.add(skill);


        mAdapter.notifyDataSetChanged();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private AddTeamSkills.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final AddTeamSkills.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
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
                for (int i=0 ; i < jsonArray.length() ; i++)
                {
                    JSONObject categoryObject = jsonArray.getJSONObject(i);
                    CategorySkills category = new CategorySkills();
                    category.setId(categoryObject.getInt("id"));
                    category.setName(categoryObject.getString("name"));
                    JSONArray skillsArray = categoryObject.getJSONArray("skill");

                    ArrayList<SkillInCategory> skills = new ArrayList<>();
                    for (int j=0 ; j < skillsArray.length() ; j++)
                    {
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
        inflater.inflate(R.menu.menu_add_skill, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_skill) {
            viewAlertDialog();
        }
        if(id == R.id.done_add_skill){

            fragment = new TeamUsersList();

            Bundle dataId = new Bundle();
            dataId.putInt("ownerId", getArguments().getInt("ownerId"));
            dataId.putInt("skillId", getArguments().getInt("skillId"));
            fragment.setArguments(dataId);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.teamAddSkill_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }


    public void viewAlertDialog() {

        String[] s = {"ahmde", "Arica", "India ", "Arica", "India ", "Arica", "India ", "Arica", "India ", "Arica"};
        ArrayList<String> categoryName = new ArrayList<>();
        final ArrayList<String> skillName = new ArrayList<>();

        for (CategorySkills categorySkills : categorySkillsArrayList)
        {
            categoryName.add(categorySkills.getName());

        }

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.alertdialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(promptsView);


        final Spinner categorySpinner = (Spinner) promptsView.findViewById(R.id.catspinner);
        final Spinner skillSpinner = (Spinner) promptsView.findViewById(R.id.skillspinner);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryName);
        final ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, skillName);

        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adp);
        skillSpinner.setAdapter(adp2);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                MyToast.toast(getContext(), name + " : name");
                skillName.clear();
                for (CategorySkills categorySkills : categorySkillsArrayList)
                {
                    if(categorySkills.getName().equals(name))
                    {
                        for (SkillInCategory skillInCategory : categorySkills.getSkills())
                        {
                            skillName.add(skillInCategory.getName());
                        }
                    }

                    adp2.notifyDataSetChanged();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setTitle("Choose Skill");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String item = (String) categorySpinner.getSelectedItem();
                MyToast.toast(getContext(), item + " : item");
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


}
