package com.example.ria.healthy.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ria.healthy.sleep.Sleep;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private String tableName = "table_" + FirebaseAuth.getInstance().getCurrentUser().getUid();
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, Sleep.DATABASE_NAME, null, Sleep.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", tableName);
        String CREATE_SLEEP_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(20), %s VARCHAR(10), %s VARCHAR(10), %s VARCHAR(10))",
                tableName,
                Sleep.Column.ID,
                Sleep.Column.DATE,
                Sleep.Column.SLEEP_TIME,
                Sleep.Column.WAKEUP_TIME,
                Sleep.Column.TOTAL_SLEEP_TIME);
        Log.d(TAG, CREATE_SLEEP_TABLE);
        db.execSQL(CREATE_SLEEP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_SLEEP_TABLE = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(DROP_SLEEP_TABLE);
        Log.d(TAG, "Upgrade Database from " + i + " to " + i1);
        onCreate(db);
    }

    public ArrayList<Sleep> getAllSleepObjects() {
        if (!isTableExist(tableName)) {
            createTable();
        }
        String selectQuery = "SELECT * FROM " + tableName;
        ArrayList<Sleep> sleeps = new ArrayList<>();
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {
                Sleep sleep = new Sleep(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                sleeps.add(sleep);
            }
        } catch (SQLiteException e) {
            Log.d("SQLite Error", e.getMessage());
        }
        cursor.close();
        db.close();
        return sleeps;
    }

    public void addSleep(Sleep sleep) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Sleep.Column.DATE, sleep.getDate());
        values.put(Sleep.Column.SLEEP_TIME, sleep.getSleepTime());
        values.put(Sleep.Column.WAKEUP_TIME, sleep.getWakeupTime());
        values.put(Sleep.Column.TOTAL_SLEEP_TIME, sleep.getTotalSleepTime());
        db.insert(tableName, null, values);
        db.close();
    }

    public Sleep getSleep(int id) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + tableName + " WHERE _id = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Sleep sleep = new Sleep();
        sleep.setId(cursor.getInt(0));
        sleep.setDate(cursor.getString(1));
        sleep.setSleepTime(cursor.getString(2));
        sleep.setWakeupTime(cursor.getString(3));
        sleep.setTotalSleepTime(cursor.getString(4));
        cursor.close();
        db.close();
        return sleep;
    }

    public void updateSleep(Sleep sleep) {
        db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Sleep.Column.ID, sleep.getId());
        values.put(Sleep.Column.DATE, sleep.getDate());
        values.put(Sleep.Column.SLEEP_TIME, sleep.getSleepTime());
        values.put(Sleep.Column.WAKEUP_TIME, sleep.getWakeupTime());
        values.put(Sleep.Column.TOTAL_SLEEP_TIME, sleep.getTotalSleepTime());
        db.update(tableName, values, Sleep.Column.ID + " = ? ", new String[] { String.valueOf(sleep.getId()) });
        db.close();
    }

    public void createTable() {
        db = this.getWritableDatabase();
        String CREATE_SLEEP_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s VARCHAR(20), %s VARCHAR(10), %s VARCHAR(10), %s VARCHAR(10))",
                tableName,
                Sleep.Column.ID,
                Sleep.Column.DATE,
                Sleep.Column.SLEEP_TIME,
                Sleep.Column.WAKEUP_TIME,
                Sleep.Column.TOTAL_SLEEP_TIME);
        Log.d(TAG, CREATE_SLEEP_TABLE);
        db.execSQL(CREATE_SLEEP_TABLE);
        db.close();
    }

    public boolean isTableExist(String table_name) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '"
                + table_name + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }
}
