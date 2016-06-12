package android.hmkcode.com.myapplication123.SplachScreen;

import android.content.Intent;
import android.hmkcode.com.myapplication123.Credential.LoginActivity;
import android.hmkcode.com.myapplication123.MainActivity.MainActivity;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.SessionManager.SessionManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplachScreen extends AppCompatActivity {

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splach_screen);


        session = new SessionManager(getApplicationContext());

        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(2500);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {

                    if(!session.getSessionStatus()) {
                        Intent toLogin = new Intent(SplachScreen.this, LoginActivity.class);
                        startActivity(toLogin);
                        finish();
                    }else{
                        Intent toMain = new Intent(SplachScreen.this, MainActivity.class);
                        startActivity(toMain);
                        finish();
                    }
                }
            }
        };
        timer.start();

    }
}
