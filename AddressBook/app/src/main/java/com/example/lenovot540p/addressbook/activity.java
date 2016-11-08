package com.example.lenovot540p.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Mike on 4/14/2015.
 */
public class activity{

    static final String DATABASE_NAME = "Contacts";

    SQLiteDatabase db;
    DatabaseOpenHelper databaseOpenHelper;


    public activity()
    {

    }

    public activity(Context context)
    {
        System.out.println("DB created");
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }


    public void updateContact(long num, String name, String phone, String email, String street, String city, String state, String zip) throws SQLException {

        ContentValues editContact = new ContentValues();
        editContact.put("name", name);
        editContact.put("phone", phone);
        editContact.put("email", email);
        editContact.put("street", street);
        editContact.put("city", city);
        editContact.put("state", state);
        editContact.put("zip", zip);
        open();
        db.update("contacts", editContact, "_id=" + num, null);
        close();

    }

    public void open() throws SQLException
    {
        db = databaseOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLException
    {
        if(db != null)
        {
            db.close();
        }
    }

    public long insertContact(String name, String phone, String email, String street, String city, String state, String zip) throws SQLException {
        ContentValues newContact = new ContentValues();
        newContact.put("name", name);
        newContact.put("phone", phone);
        newContact.put("email", email);
        newContact.put("street", street);
        newContact.put("city ", city);
        newContact.put("state", state);
        newContact.put("zip", zip);

        open();
        long rowID = db.insert("contacts", null, newContact);
        close();
        return rowID;
    }





    public Cursor getAllContacts()
    {
        return db.query("contacts", new String[] {"_id", "name"}, null, null, null, null, "name");
    }

    public Cursor getOneContact(long num)
    {
        return db.query("contacts", null, "_id=" +num, null, null, null, null);
    }

    public void deleteContact(long num) throws SQLException {
        open();
        db.delete("contacts", "_id=" +num, null);
        close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context,name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String createQuery = "CREATE TABLE contacts" + "(_id integer primary key autoincrement,"+"name TEXT, phone TEXT, email TEXT, " + " street TEXT, city TEXT, state TEXT, zip TEXT);";
            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }
}
