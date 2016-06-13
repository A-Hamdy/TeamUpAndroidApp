package android.hmkcode.com.myapplication123.CreateTeam;

import android.content.Context;
import android.hmkcode.com.myapplication123.Classes.Skill;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.hmkcode.com.myapplication123.R;

import java.util.ArrayList;
import java.util.List;


public class AddTeamSkills extends Fragment {

    private List<Skill> skillList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SkillAdapter mAdapter;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_team_skills, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_skill);

        mAdapter = new SkillAdapter(skillList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();


    }

    // TODO: Rename method, update argument and hook method into UI event
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
        Skill skill = new Skill("Computer Science", "Information Techonloy Skill", "Poor");
        skillList.add(skill);

        skill = new Skill("Computer Tech", "Information  Skill", "Good");
        skillList.add(skill);

        skill = new Skill("Science", "Techonloy Skill", "Excellent");
        skillList.add(skill);


        mAdapter.notifyDataSetChanged();
    }

}
