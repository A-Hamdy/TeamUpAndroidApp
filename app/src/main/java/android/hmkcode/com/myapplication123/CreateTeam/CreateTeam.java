package android.hmkcode.com.myapplication123.CreateTeam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.Classes.Team;
import android.hmkcode.com.myapplication123.Classes.User;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.hmkcode.com.myapplication123.R;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;


public class CreateTeam extends Fragment {

    Team team = null;
    EditText  name, description, bio, duration;
    Bitmap photo;
    Bitmap photoDefault;
    String photoDefaultBytes;
    ImageView teamImage;
    Fragment fragment;
    String imgEncode;

    public CreateTeam() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_create_team, container, false);
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = (EditText) getView().findViewById(R.id.createTeamName);
        description = (EditText) getView().findViewById(R.id.createTeamDescription);
        bio = (EditText) getView().findViewById(R.id.createTeamBio);
        duration = (EditText) getView().findViewById(R.id.createTeamDuration);
        teamImage = (ImageView) getView().findViewById(R.id.team_image);

        teamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCamera();
            }
        });


        photoDefault = BitmapFactory.decodeResource(getResources(),R.drawable.albumme);
        photoDefaultBytes = encodeToBase64(photoDefault, Bitmap.CompressFormat.PNG,100);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    void getCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1888);
        // CAMERA_REQUEST = 1888 we use request code cause onActivityResult may be used from different intents.
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == getActivity().RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            teamImage.setImageBitmap(photo);

            imgEncode = encodeToBase64(photo, Bitmap.CompressFormat.PNG, 100);


        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_team_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.done_team_info) {
            validate();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveTeamData() {

        User user = new User();
        user = user.userGetData(getContext());

        team = new Team();
        team.setTitle(name.getText().toString());
        team.setBio(bio.getText().toString());
        team.setDescription(description.getText().toString());
        team.setDurtion(Integer.parseInt(duration.getText().toString()));
        if (photo != null)
            team.setImage(imgEncode);
        else
            team.setImage(photoDefaultBytes);
        team.setOwnerId(Integer.parseInt(user.getId()));
        
        try {
            team.teamSaveData(getContext(),team);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fragment = new AddTeamSkills();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.createTeam, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    public boolean validate() {
        boolean valid = true;

        String tname = name.getText().toString();
        String tbio = bio.getText().toString();
        String tdescription = description.getText().toString();
        String tduration = duration.getText().toString();


        if (tname.isEmpty()) {
            name.setError("Required field");
            valid = false;
        } else {
            name.setError(null);
        }

        if (tbio.isEmpty()) {
            bio.setError("Required field");
            valid = false;
        } else {
            bio.setError(null);
        }

        if (tdescription.isEmpty()) {
            description.setError("Required field");
            valid = false;
        } else {
            description.setError(null);
        }

        if (tduration.isEmpty()) {
            duration.setError("Required field");
            valid = false;
        } else {
            duration.setError(null);
        }

        if (valid) {
            saveTeamData();
        }

        return valid;
    }




}