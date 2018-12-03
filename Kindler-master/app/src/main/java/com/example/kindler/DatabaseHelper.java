package com.example.kindler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.kindler.models.UserDetailModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserDetailModelManager.db";

    // UserDetailModel table name
    private static final String TABLE_UserDetailModel = "UserDetailModel";

    // UserDetailModel Table Columns names
    private static final String COLUMN_UserDetailModel_ID = "UserDetailModel_id";
    private static final String COLUMN_UserDetailModel_NAME = "UserDetailModel_name";
    private static final String COLUMN_UserDetailModel_EMAIL = "UserDetailModel_email";
    private static final String COLUMN_UserDetailModel_PASSWORD = "UserDetailModel_password";

    // create table sql query
    private String CREATE_UserDetailModel_TABLE = "CREATE TABLE " + TABLE_UserDetailModel + "("
            + COLUMN_UserDetailModel_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_UserDetailModel_NAME + " TEXT,"
            + COLUMN_UserDetailModel_EMAIL + " TEXT," + COLUMN_UserDetailModel_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_UserDetailModel_TABLE = "DROP TABLE IF EXISTS " + TABLE_UserDetailModel;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_UserDetailModel_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop UserDetailModel Table if exist
        db.execSQL(DROP_UserDetailModel_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create UserDetailModel record
     *
     * @param UserDetailModel
     */
    public void addUserDetailModel(UserDetailModel UserDetailModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UserDetailModel_NAME, UserDetailModel.getName());
        values.put(COLUMN_UserDetailModel_EMAIL, UserDetailModel.getEmail());
        values.put(COLUMN_UserDetailModel_PASSWORD, UserDetailModel.getPassword());

        // Inserting Row
        db.insert(TABLE_UserDetailModel, null, values);
        db.close();
    }

    /**
     * This method is to fetch all UserDetailModel and return the list of UserDetailModel records
     *
     * @return list
     */
    public List<UserDetailModel> getAllUserDetailModel() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_UserDetailModel_ID,
                COLUMN_UserDetailModel_EMAIL,
                COLUMN_UserDetailModel_NAME,
                COLUMN_UserDetailModel_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_UserDetailModel_NAME + " ASC";
        List<UserDetailModel> UserDetailModelList = new ArrayList<UserDetailModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the UserDetailModel table
        /**
         * Here query function is used to fetch records from UserDetailModel table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT UserDetailModel_id,UserDetailModel_name,UserDetailModel_email,UserDetailModel_password FROM UserDetailModel ORDER BY UserDetailModel_name;
         */
        Cursor cursor = db.query(TABLE_UserDetailModel, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserDetailModel UserDetailModel = new UserDetailModel();
                UserDetailModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_UserDetailModel_ID))));
                UserDetailModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_UserDetailModel_NAME)));
                UserDetailModel.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_UserDetailModel_EMAIL)));
                UserDetailModel.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_UserDetailModel_PASSWORD)));
                // Adding UserDetailModel record to list
                UserDetailModelList.add(UserDetailModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return UserDetailModel list
        return UserDetailModelList;
    }

    /**
     * This method to update UserDetailModel record
     *
     * @param UserDetailModel
     */
    public void updateUserDetailModel(UserDetailModel UserDetailModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UserDetailModel_NAME, UserDetailModel.getName());
        values.put(COLUMN_UserDetailModel_EMAIL, UserDetailModel.getEmail());
        values.put(COLUMN_UserDetailModel_PASSWORD, UserDetailModel.getPassword());

        // updating row
        db.update(TABLE_UserDetailModel, values, COLUMN_UserDetailModel_ID + " = ?",
                new String[]{String.valueOf(UserDetailModel.getId())});
        db.close();
    }

    /**
     * This method is to delete UserDetailModel record
     *
     * @param UserDetailModel
     */
    public void deleteUserDetailModel(UserDetailModel UserDetailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete UserDetailModel record by id
        db.delete(TABLE_UserDetailModel, COLUMN_UserDetailModel_ID + " = ?",
                new String[]{String.valueOf(UserDetailModel.getId())});
        db.close();
    }

    /**
     * This method to check UserDetailModel exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUserDetailModel(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_UserDetailModel_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_UserDetailModel_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query UserDetailModel table with condition
        /**
         * Here query function is used to fetch records from UserDetailModel table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT UserDetailModel_id FROM UserDetailModel WHERE UserDetailModel_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_UserDetailModel, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check UserDetailModel exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUserDetailModel(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_UserDetailModel_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_UserDetailModel_EMAIL + " = ?" + " AND " + COLUMN_UserDetailModel_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query UserDetailModel table with conditions
        /**
         * Here query function is used to fetch records from UserDetailModel table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT UserDetailModel_id FROM UserDetailModel WHERE UserDetailModel_email = 'jack@androidtutorialshub.com' AND UserDetailModel_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_UserDetailModel, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}