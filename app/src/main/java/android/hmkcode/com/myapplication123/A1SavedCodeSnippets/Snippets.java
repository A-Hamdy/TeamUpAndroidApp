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
    #9- AlertDialog with Spinners

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



    [#9]
    -----------------------------------------------------------------------------------------
    ArrayList<String> categoryName = new ArrayList<>();
    final ArrayList<String> skillName = new ArrayList<>();
    for(CategorySkills categorySkills:categorySkillsArrayList)
    {
        categoryName.add(categorySkills.getName());

    }

    LayoutInflater li = LayoutInflater.from(getContext());
    View promptsView = li.inflate(R.layout.alertdialog_view, null);
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setView(promptsView);

    final Spinner categorySpinner = (Spinner) promptsView.findViewById(R.id.catspinner);
    final Spinner skillSpinner = (Spinner) promptsView.findViewById(R.id.skillspinner);

    ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryName);
    final ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, skillName);

    adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    categorySpinner.setAdapter(adp);
    skillSpinner.setAdapter(adp2);

    categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

    {
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
        String name = (String) parent.getItemAtPosition(position);
        MyToast.toast(getContext(), name + " : name");
        skillName.clear();
        for (CategorySkills categorySkills : categorySkillsArrayList) {
            if (categorySkills.getName().equals(name)) {
                for (SkillInCategory skillInCategory : categorySkills.getSkills()) {
                    skillName.add(skillInCategory.getName());
                }
            }
            adp2.notifyDataSetChanged();
        }
    }
        @Override
        public void onNothingSelected (AdapterView < ? > parent){}
    }

    );
    builder.setTitle("Choose Skill");
    builder.setPositiveButton("OK",new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick (DialogInterface dialog,int which){
        String categoryName = (String) categorySpinner.getSelectedItem();
        String skillName = (String) skillSpinner.getSelectedItem();
        int id = GetSkillID(skillName);
        MyToast.toast(getContext(), categoryName + " : CATEGORY - " + skillName + "SKILL = ID :" + id);

        prepareMovieData(categoryName, skillName);
    }
    }

    );

    builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()

    {
        @Override
        public void onClick (DialogInterface dialog,int which){
    }
    }

    );

    final AlertDialog alertDialog = builder.create();
    alertDialog.show();
    alertDialog.setCanceledOnTouchOutside(true);

}

    int GetSkillID(String skillname) {
        int skillID = 0;

        for (CategorySkills categorySkills : categorySkillsArrayList) {
            for (SkillInCategory skillInCategory : categorySkills.getSkills()) {
                if (skillInCategory.getName().equals(skillname)) {
                    skillID = skillInCategory.getId();
                }
            }

        }

        return skillID;
    }

-----------------------------------------------------------------------------------------


        */


        }
