package com.example.qili.metcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "patient_info";

    // Contacts table name
    private static final String TABLE_paient = "patient";

    //    //    // Contacts Table Columns names
    private static final String KEY_patientUR = "patientUR";
    private static final String KEY_gender = "gender";
    private static final String KEY_smoking = "smoking";
    private static final String KEY_alcohol = "alcohol";
    private static final String KEY_emone = "emone";
    private static final String KEY_operations = "operations";
    private static final String KEY_anaesthetic = "anaesthetic";
    private static final String KEY_age = "age";

    public Databasehandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_patient="CREATE TABLE " + TABLE_paient + "("
                + KEY_patientUR +" TEXT,"
                + KEY_gender +" TEXT,"
                + KEY_smoking +" TEXT,"
                + KEY_alcohol  +" TEXT,"
                + KEY_emone +" TEXT,"
                + KEY_operations +" TEXT,"
                + KEY_anaesthetic +" TEXT,"
                + KEY_age +" TEXT" + ")";

        db.execSQL(CREATE_TABLE_patient);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_paient);

        // Create tables again
        onCreate(db);
    }

    //Insert values to the table patient
    public void addpatient(Patient patient){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_patientUR, patient.getPatientUR());
        values.put(KEY_gender, patient.getGender() );
        values.put(KEY_smoking, patient.getSmoking());
        values.put(KEY_alcohol, patient.getAge());
        values.put(KEY_emone, patient.getEmnone());
        values.put(KEY_operations, patient.getOperations());
        values.put(KEY_anaesthetic, patient.getAnaesthetic());
        values.put(KEY_age, patient.getAge());

        db.insert(TABLE_paient, null, values);
        db.close();
    }
}