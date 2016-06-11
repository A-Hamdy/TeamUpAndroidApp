package android.hmkcode.com.myapplication123;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import Classes.User;

public class MainActivity extends AppCompatActivity {
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    SessionManager session;
    User myuser;

    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        session = new SessionManager(getApplicationContext());
        session.setSession(true);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragment = new TestFragment();
        //Fragment fragment = new Fragment();
        transaction.add(R.id.mainFragment,fragment);
        transaction.commit();
        myuser = new User();
//        ActionBar a = getSupportActionBar();
//        a.show();

        myuser = myuser.userGetData(getApplicationContext());
        String data = myuser.getProfilePictureBase64();
        byte[] myimg = Base64.decode(data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
//        Bitmap bmap = myuser.imageGetFromInternal(getApplicationContext(),"userImage");

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName(myuser.getName()).withEmail(myuser.getEmail()).withIcon(decodedByte);
//        final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header2)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();


        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDrawerLayout(R.layout.crossfade_material_drawer)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_second).withIcon(FontAwesome.Icon.faw_group).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_first).withIcon(MaterialDesignIconic.Icon.gmi_account_add).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_notify).withIcon(FontAwesome.Icon.faw_bell).withIdentifier(3).withBadge("10").withBadgeStyle(new BadgeStyle(R.drawable.shap, Color.RED, Color.RED, Color.WHITE)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_third).withIcon(FontAwesome.Icon.faw_user).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fourth).withIcon(FontAwesome.Icon.faw_comments).withIdentifier(5).withBadge("2").withBadgeStyle(new BadgeStyle(R.drawable.shap, Color.RED, Color.RED, Color.WHITE)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fifth).withIcon(FontAwesome.Icon.faw_send).withIdentifier(6),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_sixth).withIcon(FontAwesome.Icon.faw_mail_reply).withIdentifier(7)
//                        new DividerDrawerItem(), // to add divider
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1) {

                            fragment = new TestFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            toolbar.setTitle("My Teams");
                            transaction.replace(R.id.mainFragment, fragment);
                            transaction.commit();
                            result.getDrawerLayout().closeDrawer(GravityCompat.START);

                        } else if (drawerItem.getIdentifier() == 2) {




                        } else if (drawerItem.getIdentifier() == 7) {

                            session.setSession(false);
                            Intent toIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(toIntent);
                            finish();

                        } else if (drawerItem.getIdentifier() == 4) {

//                            session.setSession(false);
//                            Intent toIntent = new Intent(getApplicationContext(), MyProfile.class);
//                            startActivity(toIntent);
//                            finish();
                            fragment = new MyProfile();
                            toolbar.setTitle("Profile");
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.mainFragment, fragment);
                            transaction.commit();
                            result.getDrawerLayout().closeDrawer(GravityCompat.START);


                        } else {
                            if (drawerItem instanceof Nameable) {
                                Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //get out our drawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
//                if (isFaded) {
//                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
//                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

        //hook to the crossfade event
        crossfadeDrawerLayout.withCrossfadeListener(new CrossfadeDrawerLayout.CrossfadeListener() {
            @Override
            public void onCrossfade(View containerView, float currentSlidePercentage, int slideOffset) {
                //Log.e("CrossfadeDrawerLayout", "crossfade: " + currentSlidePercentage + " - " + slideOffset);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.toRegister) {
//            Intent toRegisterIntent = new Intent(this, MainActivity.class);
//            startActivity(toRegisterIntent);
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}

