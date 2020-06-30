package co.sqlite.patientinfo.handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import co.sqlite.patientinfo.model.PatientModelClass

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "PatientDatabase"
        private val TABLE_CONTACTS = "PatientTable"
        private val KEY_ID = "id"
        private val KEY_PATIENT_NAME = "patientname"
        private val KEY_AGE = "age"
        private val KEY_ADDRESS = "address"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PATIENT_NAME + " TEXT,"
                + KEY_AGE + " TEXT,"
                + KEY_ADDRESS + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addEmployee(patient: PatientModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, patient.userId)
        contentValues.put(KEY_PATIENT_NAME, patient.patientName)
        contentValues.put(KEY_AGE,patient.patientAge)
        contentValues.put(KEY_ADDRESS,patient.patientAddress)
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }
    //method to read data
    fun viewEmployee():List<PatientModelClass>{
        val patientList:ArrayList<PatientModelClass> = ArrayList<PatientModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var patientName: String
        var patientAge: String
        var patientAddress: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                patientName = cursor.getString(cursor.getColumnIndex("patientname"))
                patientAge = cursor.getString(cursor.getColumnIndex("age"))
                patientAddress = cursor.getString(cursor.getColumnIndex("address"))
                val emp= PatientModelClass(
                    userId = userId,
                    patientName = patientName,
                    patientAge = patientAge,
                    patientAddress = patientAddress
                )
                patientList.add(emp)
            } while (cursor.moveToNext())
        }
        return patientList
    }

}