package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "passkeeper.db";

    // Таблица пользователей
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_KEY = "encryption_key";

    // Таблица паролей
    private static final String TABLE_PASSWORDS = "passwords";
    private static final String COLUMN_PASSWORD_ID = "id";
    private static final String COLUMN_USER_ID_FK = "user_id";
    private static final String COLUMN_SERVICE_NAME = "service_name";
    private static final String COLUMN_LOGIN_PW = "login";
    private static final String COLUMN_ENCRYPTED_PASSWORD = "encrypted_password";
    private static final String COLUMN_NOTES = "notes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_LOGIN + " TEXT UNIQUE," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_KEY + " TEXT" +
                ")";
        String createPasswordsTable = "CREATE TABLE " + TABLE_PASSWORDS + " (" +
                COLUMN_PASSWORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_ID_FK + " INTEGER," +
                COLUMN_SERVICE_NAME + " TEXT," +
                COLUMN_LOGIN_PW + " TEXT," +
                COLUMN_ENCRYPTED_PASSWORD + " TEXT," +
                COLUMN_NOTES + " TEXT," +
                "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createUsersTable);
        db.execSQL(createPasswordsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Обновление схемы
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, user.getLogin());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_KEY, user.getEncryptionKey());
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public User getUser(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_LOGIN + "=?", new String[]{login}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String key = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KEY));
            cursor.close();
            return new User(id, login, password, key);
        }
        return null;
    }

    public boolean insertPassword(Password password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID_FK, password.getUserId());
        values.put(COLUMN_SERVICE_NAME, password.getServiceName());
        values.put(COLUMN_LOGIN_PW, password.getLogin());
        values.put(COLUMN_ENCRYPTED_PASSWORD, password.getEncryptedPassword());
        values.put(COLUMN_NOTES, password.getNotes());
        long result = db.insert(TABLE_PASSWORDS, null, values);
        return result != -1;
    }
}