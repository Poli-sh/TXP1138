package com.example.prilogulka.data_base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.prilogulka.data.User;

import java.util.ArrayList;
import java.util.List;


public class UserLoginDB extends SQLiteOpenHelper implements LoginDataBase{

    public UserLoginDB(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(CLASS_TAG, DATA_BASE_QUERRY);
        sqLiteDatabase.execSQL(DATA_BASE_QUERRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            System.out.println("UPGRADE DB oldVersion=" + oldVersion + " - newVersion=" + newVersion);
            onCreate(sqLiteDatabase);
            if (oldVersion < 10) {
                sqLiteDatabase.execSQL(DATA_BASE_QUERRY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        System.out.println("DOWNGRADE DB oldVersion=" + oldVersion + " - newVersion=" + newVersion);
    }

    public void insertUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "INSERT OR IGNORE INTO " + DATA_BASE_NAME + " VALUES (";
        query += "null, '" + user.getEmail() + "', '" + user.getPassword() + "', '";
        query += user.getEmailCheckCode() + "', " + (user.getEmailChecked() ? 1 : 0)  + ");";

        Log.i(CLASS_TAG, query);

        database.execSQL(query);
        database.close();
    }

    @Override
    public int getUsersCount(List<User> userList) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + DATA_BASE_NAME, null);
        return cursor.getInt(0);
    }

    @Override
    public List<User> selectAll() {
        return findUser(null, null);
    }

    public List<User> findUser(String column, String filter) {
        String query;
        if (column == null || filter == null)
            query = SELECT_ALL_QUERRY;
        else
            query = "SELECT * FROM " + DATA_BASE_NAME + " WHERE " + column + " LIKE " + "'" + filter + "';";


        List<User> userList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        while(cursor.moveToNext()) {
            userList.add( new User(
               cursor.getInt(0), // id
                    cursor.getString(1), // email
                    cursor.getString(2), // password
                    cursor.getString(3), // email_check_code
                    cursor.getInt(4) == 1 // is_email_checked

            ));
        }
        return userList;
    }

    @Override
    public void dropTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + DATA_BASE_NAME);
    }

    @Override
    public void createTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(DATA_BASE_QUERRY);
    }
}


