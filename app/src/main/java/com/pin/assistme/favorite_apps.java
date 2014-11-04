package com.pin.assistme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tomer on 07/07/14.
 */
public class favorite_apps {
    String result = "";
    private final Context context;
    private SQLiteDatabase database;
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "app_name";
    public static final String KEY_OPENED = "app_opened";
    public static final String DATABASE_NAME = "favorite_apps";
    public static final String DATABASE_TABLE = "apps_table";
    public static int DATABASE_VERSION = 1;
    private Dbhelper helper;

    public long createEntry(String appname, int timesOpened) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, appname);
        cv.put(KEY_OPENED, timesOpened);
        return database.insert(DATABASE_TABLE, null, cv);
    }

    public void find_clause(String appname, int TimesOpened) {
        database.execSQL(String.format("UPDATE %s SET %s='%s' WHERE %s='%s'",
                DATABASE_TABLE, KEY_OPENED, TimesOpened, KEY_NAME, appname));
    }

    public int position(String name) {
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_OPENED};
        Cursor c = database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        int pos = 0;
        int i = 0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(i).equals(name)) {
                pos = i;
            } else {
                i++;
            }
        }
        return pos;
    }

    public String getData() {
            String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_OPENED};
            Cursor c = database.query(DATABASE_TABLE, columns, null, null, null, null, "CAST( " + KEY_OPENED + " AS INTEGER)");
            int iRow = c.getColumnIndex(KEY_ROWID);
            int iAppName = c.getColumnIndex(KEY_NAME);
            int iTimesOpened = c.getColumnIndex(KEY_OPENED);
            try {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    if(!result.contains(c.getString(iTimesOpened) + "|| " + c.getString(iAppName) + "\n")) {
                        result = result + c.getString(iTimesOpened) + "|| " + c.getString(iAppName) + "\n";
                    }
                }
            }
            catch (IllegalStateException e){
                e.printStackTrace();
            }
        return result;
    }


    public void delete(String appname)
    {
        database.delete(DATABASE_TABLE,KEY_ROWID+" "+position(appname),null);
    }

    private static class Dbhelper extends SQLiteOpenHelper{
    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                KEY_ROWID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT NOT NULL, "+
                KEY_OPENED + " TEXT NOT NULL);"

    );
        db.query(DATABASE_TABLE, new String[] { KEY_ROWID,
                        KEY_NAME }, null, null,
                null, null, KEY_NAME+ " ASC");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+DATABASE_TABLE);
        onCreate(db);
    }
}
    public favorite_apps(Context c){
        context = c;
    }
    public favorite_apps open()
    {
        helper = new Dbhelper(context);
        database = helper.getWritableDatabase();
        return this;
    }
    public void close(){
        helper.close();
    }
}
