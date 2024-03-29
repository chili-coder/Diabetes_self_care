package com.chilicoder.diabetesself_care.blood;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chilicoder.diabetesself_care.followup.FollowupItem;
import com.chilicoder.diabetesself_care.tobacco.Tobacco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelperBlood  extends SQLiteOpenHelper {


    private static final String DB_NAME = "Bloodsreminder";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Blood";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_REPORT= "Report";

    private static final String KEY_DAY = "Day";
    private static final String KEY_MONTH = "Month";
    private static final String KEY_YEAR= "Year";

    private static final String KEY_TIME= "Time";
    private static final String KEY_DATE= "Date";
    private static final String KEY_TIMES_PER_DAY= "TimesPerDay";
    private static final String KEY_TOTAL_DOSES= "TotalDoses";
    private static final String KEY_TIMINGS= "Timings";
    private static final String KEY_ALERT_TYPE = "AlertType";
    public DatabaseHelperBlood(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL);", DB_TABLE, KEY_ID, KEY_NAME,KEY_REPORT, KEY_DAY, KEY_MONTH, KEY_YEAR,KEY_TIME,KEY_DATE, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES, KEY_TIMINGS, KEY_ALERT_TYPE);

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryUpgrade = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        sqLiteDatabase.execSQL(queryUpgrade);
        onCreate(sqLiteDatabase);
    }

    public void insertNewBloods(String bloodName,String repost,String mTime,String mDate, int day, int month,
                                  int year, int noOfTimesPerDay, int totalDoses, String timings, String alertType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, bloodName);
        values.put(KEY_REPORT, repost);
        values.put(KEY_DAY , day);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_TIME, mTime);
        values.put(KEY_DATE, mDate);
        values.put(KEY_TIMES_PER_DAY, noOfTimesPerDay);
        values.put(KEY_TOTAL_DOSES, totalDoses);
        values.put(KEY_TIMINGS, timings);
        values.put(KEY_ALERT_TYPE, alertType);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i("Follow up DB Helper",  bloodName + day + month + year + noOfTimesPerDay + totalDoses + timings + alertType);
        db.close();
    }

    public void deleteBloods(String dietName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, KEY_NAME + " = ?", new String[]{dietName});
        db.close();
    }


    public List<BloodItem> getBloodList() {
        List<BloodItem> dietList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME,KEY_REPORT,KEY_TIME,KEY_DATE, KEY_TIMES_PER_DAY}, null, null, null, null, KEY_ID+" DESC");
        while (cursor.moveToNext()) {
            BloodItem homeItem = new BloodItem(cursor.getString(0) , cursor.getString(1) ,cursor.getString(2),cursor.getString(3));
            dietList.add(homeItem);
        }
        cursor.close();
        db.close();
        return  dietList;

    }

    public int getId(String name) {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_TIMES_PER_DAY}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }



    public List<String> getTimings(String medicineName) {
        List<String> timingList = new ArrayList<>();
        StringBuffer timingsString = new StringBuffer();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_TIMINGS}, KEY_NAME + " = ?", new String[]{medicineName}, null, null, null);
        while (cursor.moveToNext()) {
            timingsString.append(cursor.getString(0));
            Log.i("Timings", timingsString.toString());
        }
        JSONObject json = null;
        try {
            json = new JSONObject(new String(timingsString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray items = json.optJSONArray("timingArrays");
        for (int i = 0; i < items.length(); i++) {
            try {
                timingList.add(items.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return timingList;
    }

    public int noOfDaysLeft(String dietName, Calendar mNextAlarmDate) {
        int mPerDay = 0, mTotalDodes = 0;
        int day = 0, month = 0, year = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES}, KEY_NAME + " = ?", new String[]{dietName}, null, null, null);
        while (cursor.moveToNext()) {
            day = cursor.getInt(0);
            month = cursor.getInt(1);
            year = cursor.getInt(2);
            mPerDay = cursor.getInt(3);
            mTotalDodes = cursor.getInt(4);
        }
        int totalDays = mTotalDodes/mPerDay;
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, day);
        startDate.set(Calendar.MONTH, month);
        startDate.set(Calendar.YEAR, year);
        long daysBetween = Math.round((float) (mNextAlarmDate.getTimeInMillis() - startDate.getTimeInMillis()) / (72 * 60 * 60 * 1000));
        int daysLeft = (int) (totalDays - daysBetween);
        return daysLeft;
    }

}
