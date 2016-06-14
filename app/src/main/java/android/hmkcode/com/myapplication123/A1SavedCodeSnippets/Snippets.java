package android.hmkcode.com.myapplication123.A1SavedCodeSnippets;

/**
 * Created by Ahmed Hamdy on 6/14/2016.
 */
public class Snippets {

    /*

    List :
    #0- Android Studio Tips.
    #1- Image Encode and Decode.
    #2- Json Parse using JsonObject.
    #3- Json Parse Array using JsonObject.
    #4- Json Parse using Gson toJson.
    #5- Json Parse using Gson fromJson.
    #6- Save Image To Internal Storage [Not Recommended Gallery Better].
    #7- Get Image from Internal Storage.
    #8- Singleton Design Pattern

     */


    /*

    [#]
    -----------------------------------------------------------------------------------------
    // Code
    -----------------------------------------------------------------------------------------


    [#0]
    -----------------------------------------------------------------------------------------
    // Crop Selection
    - ALT + select using Mouse.

    //Select multiple scattered lines
    - SHIFT + ALT + Mouse click.

    //Search EveryWhere
    - Double Shift.

    //Quick Format
    - CTRL + ALT + L.
    -----------------------------------------------------------------------------------------


    [#1]
    -----------------------------------------------------------------------------------------
        //Encode
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.albumme);
        String imgEncode = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);
        public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            image.compress(compressFormat, quality, byteArrayOS);
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        }

        //Decode
        String data = myuser.getProfilePictureBase64();
        byte[] myimg = Base64.decode(data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
    -----------------------------------------------------------------------------------------


    [#2]
    -----------------------------------------------------------------------------------------
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "title2");
        jsonObject.put("bio","biooooooo" );
        jsonObject.put("description","description");
        jsonObject.put("durtion","5");
        jsonObject.put("ownerId","69");
        jsonObject.put("skillId","1");
        jsonObject.put("image","ahmed");
    -----------------------------------------------------------------------------------------


    [#8]
    -----------------------------------------------------------------------------------------
        private static User userSingleton = null;
        private User(){ }
        public static User getInstance(){
            if(userSingleton == null)
                userSingleton = new User();
            return userSingleton;
        }
    -----------------------------------------------------------------------------------------








     */


}
