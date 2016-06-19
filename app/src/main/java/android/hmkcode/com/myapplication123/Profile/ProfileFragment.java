package android.hmkcode.com.myapplication123.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.hmkcode.com.myapplication123.Classes.User;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class ProfileFragment extends Fragment {

    TextView et_name,
            et_email,
            et_linkedIn,
            et_phone,
            et_bio;


    ImageView image1,
            image2, image3, image4, image5, image6;
    //final Context context = this;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    User user;
    Bitmap decodedByte;
    private GoogleApiClient client;

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
        et_name = (TextView) getView().findViewById(R.id.F1Name);
        et_email = (TextView) getView().findViewById(R.id.F1Email);
        et_phone = (TextView) getView().findViewById(R.id.F1Phone);
        et_bio = (TextView) getView().findViewById(R.id.F1Bio);
        et_linkedIn = (TextView) getView().findViewById(R.id.F1Linked);

        image1 = (ImageView) getView().findViewById(R.id.imageView);

        image2 = (ImageView) getView().findViewById(R.id.imageView2);
        image3 = (ImageView) getView().findViewById(R.id.imageView3);
        image4 = (ImageView) getView().findViewById(R.id.imageview4);
        image5 = (ImageView) getView().findViewById(R.id.imageview5);
        image6 = (ImageView) getView().findViewById(R.id.imageview6);


        MyToast.toast(getActivity().getApplicationContext(), user.getName() + " :::");
        et_name.setText(user.getName());
        et_email.setText(user.getEmail());
        et_phone.setText(user.getPhone());
        et_bio.setText(user.getBio());
        et_linkedIn.setText(user.getLinkedIn());
//        image1.setImageBitmap(decodedByte);
//        image2.setImageBitmap(decodedByte);
//        image3.setImageBitmap(decodedByte);
//        image4.setImageBitmap(decodedByte);
//        image5.setImageBitmap(decodedByte);
//        image6.setImageBitmap(decodedByte);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }


        });


//
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Your Name Here"); //Set Alert dialog title here
                // alert.setMessage("Enter Your Name Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        String srt = input.getEditableText().toString();
                        et_name.setText(srt);
                        //   Toast.makeText(context,srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }// End of onClick(View v)


        });


        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Your Email Here"); //Set Alert dialog title here
                //    alert.setMessage("Enter Your Name Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        String srt = input.getEditableText().toString();
                        et_email.setText(srt);
                        //  Toast.makeText(context,srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }// End of onClick(View v)


        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Your LinkedUrl Here"); //Set Alert dialog title here
                // alert.setMessage("Enter Your Name Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        String srt = input.getEditableText().toString();
                        et_linkedIn.setText(srt);
                        // Toast.makeText(context,srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }// End of onClick(View v)


        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Your phone Here"); //Set Alert dialog title here
                // alert.setMessage("Enter Your Name Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        String srt = input.getEditableText().toString();
                        et_phone.setText(srt);
                        //   Toast.makeText(context,srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }// End of onClick(View v)


        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Your Name Here"); //Set Alert dialog title here
                // alert.setMessage("Enter Your Name Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        String srt = input.getEditableText().toString();
                        et_bio.setText(srt);
                        //   Toast.makeText(context,srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/
            }// End of onClick(View v)


        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://android.hmkcode.com.myapplication123/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://android.hmkcode.com.myapplication123/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor =getActivity(). getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) getView().findViewById(R.id.F1profileImage);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
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


