package android.hmkcode.com.myapplication123.Profile;

 import android.hmkcode.com.myapplication123.R;
 import android.hmkcode.com.myapplication123.SessionManager.SessionManager;
 import android.os.Bundle;
import android.support.design.widget.TabLayout;
 import android.support.v4.app.Fragment;
 import android.support.v4.view.ViewPager;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.RelativeLayout;

 import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
 import com.mikepenz.materialdrawer.AccountHeader;
 import com.mikepenz.materialdrawer.Drawer;
 import com.mikepenz.materialdrawer.MiniDrawer;

public class MyProfile extends Fragment {


    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    SessionManager session;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        RelativeLayout ll = (RelativeLayout) inflater.inflate(R.layout.activity_my_profile, container, false);

        session = new SessionManager(getActivity());
        session.setSession(true);


        TabLayout tabLayout = (TabLayout) ll.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Profile Info").setIcon(R.drawable.profile_tab1));
        tabLayout.addTab(tabLayout.newTab().setText("Skills").setIcon(R.drawable.star_tab2));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) ll.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                ((getActivity()).getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return ll;

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_profile);


    }
}
