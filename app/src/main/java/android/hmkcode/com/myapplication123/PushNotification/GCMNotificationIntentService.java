package android.hmkcode.com.myapplication123.PushNotification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hmkcode.com.myapplication123.Classes.Notification;
import android.hmkcode.com.myapplication123.Classes.Team;
import android.hmkcode.com.myapplication123.DB.SqlLiteDataBaseHelper;
import android.hmkcode.com.myapplication123.MainActivity.MainActivity;
import android.hmkcode.com.myapplication123.Notification.Notifications;
import android.hmkcode.com.myapplication123.R;
import android.hmkcode.com.myapplication123.Utitlites.MyToast;
import android.hmkcode.com.myapplication123.Utitlites.Utilites;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GCMNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }

                }

                sendNotification((String) extras.get(Utilites.MESSAGE_KEY));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {


        Log.i("testNotifications", msg);


        JSONObject jsonResult = null;
        String notifyMessage = null;
        String teamId = null;
        String targetUserId = null;
        String teamName = null;

        try {
            jsonResult = new JSONObject(msg);
            notifyMessage = jsonResult.getString("message");
            teamId = jsonResult.getString("teamId");

            if (notifyMessage.equalsIgnoreCase("accept")) {
                targetUserId = jsonResult.getString("userId");
                teamName = jsonResult.getString("teamTitle");
            }
            String msgDetails = null;
            if (notifyMessage.equalsIgnoreCase("invite"))
                msgDetails = "You have invited to join a Team";
            else if (notifyMessage.equalsIgnoreCase("accept"))
                msgDetails = "You have accepted to join Team " + teamName;
            else if (notifyMessage.equalsIgnoreCase("reject"))
                msgDetails = "Member rejected to join your Team";
            else if (notifyMessage.equalsIgnoreCase("confirm"))
                msgDetails = "Your have confirmed to join Team " + teamName;
            else if (notifyMessage.equalsIgnoreCase("Owner reject"))
                msgDetails = "Owner rejected you to join Team " + teamName;



            Notification notification = new Notification(notifyMessage, msgDetails, teamId, targetUserId, teamName);
            SqlLiteDataBaseHelper helper = new SqlLiteDataBaseHelper(getApplicationContext());
            helper.createNotification(notification);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            Log.i("HamdyNotifi", msg);
            JSONObject notificationJSON = new JSONObject();
            notificationJSON.put("type", notifyMessage);
            notificationJSON.put("teamID", teamId);
            notificationJSON.put("teamName", teamName);
            notificationJSON.put("targetUserId", targetUserId);
            if (notifyMessage.equalsIgnoreCase("invite"))
                notificationJSON.put("msg", "You have invited to join a Team");
            if (notifyMessage.equalsIgnoreCase("accept"))
                notificationJSON.put("msg", "You have accepted to join Team " + teamName);


            //Notification.notificationsSaveData(getApplicationContext(),notificationJSON.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


//        try
//        {
//            Log.i("HamdyNotifi", msg);
//
//            JSONArray notificationsArray = null;
//
//            String notificationsJSONObjectArray = Notification.notificationsGetArrayData(getApplicationContext());
//            if (notificationsJSONObjectArray.length() < 1)
//            {
//                notificationsArray = new JSONArray(notificationsJSONObjectArray);
//            }
//            else
//            {
//                notificationsArray = new JSONArray();
//            }
//            JSONObject notificationJSON = new JSONObject();
//            notificationJSON.put("type",notifyMessage);
//            notificationJSON.put("teamID",teamId);
//            notificationJSON.put("targetUserId",targetUserId);
//            notificationJSON.put("teamName",teamName);
//
//            if (notifyMessage.equalsIgnoreCase("invite"))
//                notificationJSON.put("msg","You have invited to join a Team");
//            if (notifyMessage.equalsIgnoreCase("accept"))
//                notificationJSON.put("msg","You have accepted to join Team " + teamName);
//
//            JSONObject notificationJSON2 = new JSONObject();
//            notificationJSON2 = notificationJSON;
//            notificationsArray.put(notificationJSON2);
//            notificationsArray.put(notificationJSON);
//
//            Notification.notificationsSaveData(getApplicationContext(),notificationJSON.toString());
//
//
//        } catch (JSONException e)
//        {
//            e.printStackTrace();
//        }


        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Notifications.class), 0);

        Uri sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.cool_sound);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.logo)
                .setContentTitle("TeamUP")
                .setContentText(notifyMessage + " you to join his team")
                .setSound(sound);

        mBuilder.setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
