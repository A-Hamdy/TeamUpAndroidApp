package Classes;

/**
 * Created by Ahmed Hamdy on 6/1/2016.
 */
public class Utilites {

    static String PORT = "40663";
//    static String IP = "localhost";
//    static String IP = "168.254.154.163";
//    static String IP = "10.144.3.172";
//    static String IP = "192.168.43.21";
//    static String IP = "192.168.72.1";
    static String IP = "10.118.48.188";

    public static String SharedPref_Name = "MyUserPreference";
    public static String URL_Login = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/login";
    public static String URL_Register = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/registration";
    public static String URL_CreateTeam = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/creatTeam";
    public static String URL_EditProfile = "http://"+IP+":"+PORT+"/TeamUP/rest/WebService/editProfile";

}
