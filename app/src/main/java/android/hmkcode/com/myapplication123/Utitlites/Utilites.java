package android.hmkcode.com.myapplication123.Utitlites;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Ahmed Hamdy on 6/1/2016.
 */
public class Utilites {

    static String PORT = "40663";
//    static String PORT = "49583";
//    static String IP = "localhost";
//    static String IP = "10.118.49.61";
//    static String IP = "10.144.3.172";
//    static String IP = "192.168.43.21";
//    static String IP = "10.118.49.177";
//    static String IP = "10.140.200.120";
    static String IP = "10.118.48.221";

    public static final String MESSAGE_KEY = "message";
    public static final String GOOGLE_PROJECT_ID = "209716861273";

    public static String SharedPref_Name = "MyUserPreference";
    public static String URL                   = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/";
    public static String URL_Login             = URL + "login";
    public static String URL_Register          = URL + "registration";
    public static String URL_CreateTeam        = URL + "creatTeam";
    public static String URL_EditProfile       = URL + "editProfile";
    public static String URL_GetSuggestedUsers = URL + "getUserForNewTeam";
    public static String URL_InviteUsers       = URL + "inviteUsersToTeam";
    public static String URL_Notification_URL  = URL + "registrationGcm";
    public static String URL_GetAllCategories  = URL + "getAllCategory";
    public static String URL_GetMyTeamsAsOwner = URL + "getMyTeamAsOwner";
    public static String URL_GetMyTeamsAsMember= URL + "getMyTeamAsMember";
    public static String URL_GetTeamById       = URL + "getTeamById";
    public static String URL_AcceptInvitation  = URL + "operationOnInvitation";
    public static String URL_ConfirmInvitation = URL + "confirm";
    public static String URL_AddSkill          = URL + "addSkill";
    public static String URL_AddNewSkill       = URL + "addNewSkill";
    public static String URL_Logout            = URL + "logOut";



}
