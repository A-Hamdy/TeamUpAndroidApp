package android.hmkcode.com.myapplication123;

 import android.content.Intent;
 import android.graphics.Color;
 import android.os.Bundle;
import android.support.design.widget.TabLayout;
 import android.support.v4.app.Fragment;
 import android.support.v4.view.GravityCompat;
 import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.LinearLayout;
 import android.widget.RelativeLayout;
 import android.widget.Toast;

 import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
 import com.mikepenz.fontawesome_typeface_library.FontAwesome;
 import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
 import com.mikepenz.materialdrawer.AccountHeader;
 import com.mikepenz.materialdrawer.AccountHeaderBuilder;
 import com.mikepenz.materialdrawer.Drawer;
 import com.mikepenz.materialdrawer.DrawerBuilder;
 import com.mikepenz.materialdrawer.MiniDrawer;
 import com.mikepenz.materialdrawer.holder.BadgeStyle;
 import com.mikepenz.materialdrawer.interfaces.ICrossfader;
 import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
 import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
 import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
 import com.mikepenz.materialdrawer.model.interfaces.IProfile;
 import com.mikepenz.materialdrawer.model.interfaces.Nameable;
 import com.mikepenz.materialdrawer.util.DrawerUIUtils;
 import com.mikepenz.materialize.util.UIUtils;

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
