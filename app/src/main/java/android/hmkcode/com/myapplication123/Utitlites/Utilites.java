package android.hmkcode.com.myapplication123.Utitlites;

/**
 * Created by Ahmed Hamdy on 6/1/2016.
 */
public class Utilites {

    static String PORT = "49583";
//    static String IP = "localhost";
//    static String IP = "168.254.154.163";
//    static String IP = "10.144.3.172";
    static String IP = "10.0.0.66";
    //static String IP = "192.168.72.1";
//    static String IP = "10.140.200.63";
//    static String IP = "10.0.1.14";

    public static final String MESSAGE_KEY = "message";
    public static final String GOOGLE_PROJECT_ID = "209716861273";


    public static String SharedPref_Name = "MyUserPreference";
    public static String URL_Login = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/login";
    public static String URL_Register = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/registration";
    public static String URL_CreateTeam = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/creatTeam";
    public static String URL_EditProfile = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/editProfile";
    public static String URL_GetSuggestedUsers = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/getUserForNewTeam";
    public static String URL_InviteUsers = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/inviteUsersToTeam";



    public static String URL_Notification_URL = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/registrationGcm";
//    public static String URL_Notification_URL = "http://10.0.0.66:49583/PushNotification/GCMNotification?shareRegId=1";

}