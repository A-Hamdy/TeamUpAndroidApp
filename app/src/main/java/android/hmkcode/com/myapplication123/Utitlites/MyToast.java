package android.hmkcode.com.myapplication123.Utitlites;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Ahmed Hamdy on 5/30/2016.
 */
public class MyToast {

    public static void toast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void toast(Context context,String msg,int num){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void snackbar(View view, String msg){
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();
    }

    public static void snackbar(View view, String msg, boolean longer){
         Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show();
    }



}
