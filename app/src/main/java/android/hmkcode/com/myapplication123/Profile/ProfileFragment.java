package android.hmkcode.com.myapplication123.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.hmkcode.com.myapplication123.Classes.User;


public class ProfileFragment extends Fragment {

    EditText  name, email, bio, phone, linkedIn;
    ImageView profile;
    User user;
    Bitmap decodedByte;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        user = new User();

        user = user.userGetData(getActivity().getApplicationContext());
        String data = user.getProfilePictureBase64();
        byte[] myimg = Base64.decode(data, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.profile_tab_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (EditText) getView().findViewById(R.id.F1Name);
        email = (EditText) getView().findViewById(R.id.F1Email);
        phone = (EditText) getView().findViewById(R.id.F1Phone);
        bio = (EditText) getView().findViewById(R.id.F1Bio);
        linkedIn = (EditText) getView().findViewById(R.id.F1Linked);
        profile = (ImageView) getView().findViewById(R.id.F1profileImage);

        MyToast.toast(getActivity().getApplicationContext(),user.getName() + " :::");
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        bio.setText(user.getBio());
        linkedIn.setText(user.getLinkedIn());
        profile.setImageBitmap(decodedByte);
    }


//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuItem shareMenuItem = menu.findItem(R.id.action_share);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
