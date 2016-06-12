package android.hmkcode.com.myapplication123.Utitlites;

import android.content.Context;
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

}
