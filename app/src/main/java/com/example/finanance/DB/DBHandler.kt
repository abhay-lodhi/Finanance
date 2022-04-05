package com.example.finanance.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.finanance.model.finModelClass

class DBHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "FinMan"

        private val TABLE_TRANSACTIONS = "Transactions"
        private val TABLE_Statics = "Statics"

        private val KEY_ID = "_id"
        private val KEY_AMOUNT = "amount"
        private val KEY_TYPE = "type"
        private val KEY_DAT= "dat12"
        private val KEY_NOTE = "note"
        private val KEY_MODE = "mode"
        private val KEY_TRANSACTIONID="transactionid"
        private  val KEY_FOOD="food"
        private  val KEY_BILLS="bills"
        private  val KEY_SHOPPING="shopping"
        private val KEY_DAILY="dailyneeds"
        private  val KEY_OTHER="other"
        private val KEY_MONTHYEAR= "month year"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //p0?.execSQL("DROP TABLE "+ TABLE_TRANSACTIONS)

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AMOUNT +" INTEGER,"+ KEY_TYPE+" TEXT,"+ KEY_DAT+" TEXT,"+
                KEY_NOTE+" TEXT,"+ KEY_MODE+" TEXT,"+ KEY_TRANSACTIONID+" TEXT"+")")

       p0?.execSQL(CREATE_CONTACTS_TABLE)

        val CREATE_STATICS_TABLE = ("CREATE TABLE " + TABLE_Statics + "("
                + KEY_MONTHYEAR +" TEXT PRIMARY KEY"+ KEY_FOOD +" INTEGER,"+ KEY_BILLS +" INTEGER,"+
                 KEY_SHOPPING +" INTEGER,"+ KEY_DAILY +" INTEGER,"+
                KEY_OTHER +" INTEGER,"+ ")")
        p0?.execSQL(CREATE_STATICS_TABLE)


    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
       p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_Statics")
        onCreate(p0)
    }

    fun addTransactions(tran: finModelClass): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_AMOUNT, tran.Amount)
        contentValues.put(KEY_TYPE , tran.Type)
        contentValues.put(KEY_DAT , tran.Date)
        contentValues.put(KEY_NOTE , tran.Note)
        contentValues.put(KEY_MODE , tran.Mode)
        contentValues.put(KEY_TRANSACTIONID , tran.Transaction_id)
        // Inserting employee details using insert query.
        val success = db.insert(TABLE_TRANSACTIONS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection

        return success

    }

//    fun getRow(tran: finModelClass): finModelClass{
//      val db= this.readableDatabase
//
//    }
    }