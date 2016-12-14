package com.example.hectorvera.sqlpractice1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hectorvera.sqlpractice1.objects.Contact;

import static com.example.hectorvera.sqlpractice1.library.library.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/23/2016.
 */

public class ContactDH extends SQLiteOpenHelper{



    public ContactDH(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS +" ( "
                + CONTACT_KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACT_NAME +" TEXT, " +
                CONTACT_PH_NO +" TEXT)";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+ TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(Contact c){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, c.getName());
        values.put(CONTACT_PH_NO, c.getPhoneNumber());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=  db.query(TABLE_CONTACTS,
                new String[]{CONTACT_KEY_ID, CONTACT_NAME, CONTACT_PH_NO},
                CONTACT_KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Contact c = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        return c;
    }

    public Contact getContact (String name, String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[]{CONTACT_KEY_ID, CONTACT_NAME, CONTACT_PH_NO},
                CONTACT_NAME + "=? AND "+ CONTACT_PH_NO + "=? ",
                new String[]{name, phone},
                null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );
        return contact;
    }

    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact= new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateContact(Contact c){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, c.getName());
        values.put(CONTACT_PH_NO, c.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, CONTACT_KEY_ID + " = ?",
                new String[] { String.valueOf(c.getId()) });

    }

    public void deleteConctact(Contact c){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, CONTACT_KEY_ID + " = ?",
                new String[] { String.valueOf(c.getId()) });
        db.close();
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
