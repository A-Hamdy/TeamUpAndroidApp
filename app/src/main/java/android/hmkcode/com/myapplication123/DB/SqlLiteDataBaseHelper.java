package android.hmkcode.com.myapplication123.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hmkcode.com.myapplication123.Classes.Notification;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlLiteDataBaseHelper extends SQLiteOpenHelper {


    public static final String TABLE_Notification = "User";

    public static final String COLUMN_id = "id";
    public static final String COLUMN_msg = "msg";
    public static final String COLUMN_type = "type";
    public static final String COLUMN_teamID = "teamID";
    public static final String COLUMN_targetUserId = "targetUserId";
    public static final String COLUMN_teamName = "teamName";
    public static final String COLUMN_readed = "readed";
    private static final String DATABASE_NAME = "DB.db";
    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_CREATE_Notification = "CREATE TABLE "
            + TABLE_Notification          + " ("
            + COLUMN_id       + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_msg      + " VARCHAR(255),"
            + COLUMN_type     + " VARCHAR(255),"
            + COLUMN_teamID   + " VARCHAR(255),"
            + COLUMN_targetUserId    + " VARCHAR(255),"
            + COLUMN_teamName + " VARCHAR(255),"
            + COLUMN_readed + " INTEGER);";


    public SqlLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE_Notification);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlLiteDataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Notification);

        onCreate(db);
    }




    public void createNotification(Notification notification) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_msg, notification.getMsg());
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_type, notification.getType());
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_teamID, notification.getTeamID());
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_targetUserId, notification.getTargetUserId());
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_teamName, notification.getTeamName());
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_readed, 0);
        long insertId = database.insert(SqlLiteDataBaseHelper.TABLE_Notification, null,
                contentValues);

        database.close();

    }

    public void updateNotification(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlLiteDataBaseHelper.COLUMN_readed, 1);
        database.update(TABLE_Notification,contentValues,"id="+id,null);
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<Notification>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_Notification + " where readed <> 1", null);

        while (cursor.moveToNext()) {
            Notification notification = cursorToUser(cursor);
            notifications.add(notification);
        }
        cursor.close();
        return notifications;
    }



    private Notification cursorToUser(Cursor cursor) {
        Notification notification = new Notification();
        notification.setId(cursor.getString(cursor.getColumnIndex("id")));
        notification.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
        notification.setType(cursor.getString(cursor.getColumnIndex("type")));
        notification.setTeamID(cursor.getString(cursor.getColumnIndex("teamID")));
        notification.setTargetUserId(cursor.getString(cursor.getColumnIndex("targetUserId")));
        notification.setTeamName(cursor.getString(cursor.getColumnIndex("teamName")));
        return notification;
    }
}
