package android.hmkcode.com.myapplication123.MyTeams;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hmkcode.com.myapplication123.Classes.CategorySkills;
import android.hmkcode.com.myapplication123.Classes.SkillInCategory;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.hmkcode.com.myapplication123.MyTeams.TeamGrid.TeamGrid;
import android.hmkcode.com.myapplication123.MyTeams.TeamGrid.TeamsAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyTeams extends Fragment {

    private RecyclerView recyclerView;
    private TeamsAdapter adapter;
    private List<TeamGrid> teamList;
    User user;
    Fragment fragment;
    ProgressDialog progressDialog;
    boolean found = true;
    boolean found2 = true;


    public MyTeams() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        user = new User();
        user = user.userGetData(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.team_fragment, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        teamList = new ArrayList<>();
        adapter = new TeamsAdapter(getActivity(), teamList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
                TextView textView = (TextView) view.findViewById(R.id.teamId);
                String team_id = textView.getText().toString();


                Bundle bundle = new Bundle();
                bundle.putString("TEAMID", team_id);

                fragment = new ViewTeam();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myteampage, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(adapter);

        //prepareAlbums();
        new HttpAsyncTasks().execute(Utilites.URL_GetMyTeamsAsOwner);
        new HttpAsyncTasksMember().execute(Utilites.URL_GetMyTeamsAsMember);
    }


    private void prepareTeams(boolean owner, String teamId, String teamName, String teamMembersNum, byte[] image) {

        TeamGrid a = new TeamGrid(teamId, teamName, teamMembersNum, image, owner);
        teamList.add(a);

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public class HttpAsyncTasks extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting Teams ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", user.getId());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String result = WebServiceHandler.handler(urls[0], jsonObject);
            return result;

        }


        @Override
        protected void onPostExecute(String result) {


            try {
                JSONArray jsonArray = new JSONArray(result);


                if (jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject team = jsonArray.getJSONObject(i);

                        String teamName = team.getString("title");
                        String teamId = team.getString("id");


                        String image = team.getString("image");
                        byte[] myimg = Base64.decode(image, Base64.DEFAULT);


                        JSONArray membersArrays = team.getJSONArray("userHasTeams");
                        String teamMemberNum = membersArrays.length() + "";
                        boolean owner = true;

                        prepareTeams(owner, teamId, teamName, teamMemberNum, myimg);

                    }


                } else {
                    found = false;
                    checker();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();


        }
    }

    public class HttpAsyncTasksMember extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", user.getId());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String result = WebServiceHandler.handler(urls[0], jsonObject);
            return result;

        }


        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);

                if (jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject team = jsonArray.getJSONObject(i);

                        String teamName = team.getString("title");
                        String teamId = team.getString("id");

                        String image = team.getString("image");
                        byte[] myimg = Base64.decode(image, Base64.DEFAULT);

                        JSONArray membersArrays = team.getJSONArray("userHasTeams");
                        String teamMemberNum = membersArrays.length() + "";
                        boolean owner = false;
                        prepareTeams(owner, teamId, teamName, teamMemberNum, myimg);
                    }
                }else {
                    found2 = false;
                    checker();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();
            progressDialog.cancel();

        }
    }


    void checker(){
        if (found == false && found2 == false)
        {
            Fragment fragment = new NoTeams();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.myteampage, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_myteams, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MyTeams.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MyTeams.ClickListener clickListener) {
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


}
