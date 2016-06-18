package android.hmkcode.com.myapplication123.MainActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hmkcode.com.myapplication123.CreateTeam.CreateTeam;
import android.hmkcode.com.myapplication123.Credential.LoginActivity;
import android.hmkcode.com.myapplication123.MyTeams.MyTeams;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.SessionManager.SessionManager;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.hmkcode.com.myapplication123.WebServiceHandler.WebServiceHandler;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
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

import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.Profile.MyProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    SessionManager session;
    User myuser;
    GoogleCloudMessaging gcm;
    String regId;
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

        fragment = new MyTeams();
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

       //sendToken();

       // new HttpAsyncTask2().execute(Utilites.URL_InviteUsers);
        // Bitmap bmap = myuser.imageGetFromInternal(getApplicationContext(),"userImage");

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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_first).withIcon(FontAwesome.Icon.faw_user_plus).withIdentifier(2),
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

                            fragment = new MyTeams();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            toolbar.setTitle("My Teams");
                            transaction.replace(R.id.mainFragment, fragment);
                            transaction.commit();
                            result.getDrawerLayout().closeDrawer(GravityCompat.START);

                        } else if (drawerItem.getIdentifier() == 2) {


                            fragment = new CreateTeam();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            toolbar.setTitle("Create Team");
                            transaction.replace(R.id.mainFragment, fragment);
                            transaction.commit();
                            result.getDrawerLayout().closeDrawer(GravityCompat.START);


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



    /*Notification GCM*/
    public void sendToken(){
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


    public void SendToServer(){

        new SendTokenToServer().execute(Utilites.URL_Notification_URL);
    }


    private class SendTokenToServer extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Send Token To Server ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            String result = null;
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", myuser.getId());
                jsonObject.put("token",  regId);
                result = WebServiceHandler.handler(urls[0], jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            MyToast.toast(getApplicationContext(),"server data :" + result);
            progressDialog.dismiss();


        }


    }




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


}

