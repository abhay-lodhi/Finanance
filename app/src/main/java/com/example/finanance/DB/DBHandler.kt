package com.example.finanance.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getIntOrNull
import com.example.finanance.model.finModelClass
import com.example.finanance.model.typeModelClass
import java.time.LocalDate
import java.util.*

class DBHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "FinMan"

        private val TABLE_TRANSACTIONS = "Transactions"
        private val TABLE_Statics = "Statics"

        private val KEY_ID = "_id"
        private val KEY_AMOUNT = "amount"
        private val KEY_TYPE = "type"
        private val KEY_DAT= "dat"
        private val KEY_NOTE = "note"
        private val KEY_MODE = "mode"
        private val KEY_TRANSACTIONID="transactionid"
        private  val KEY_FOOD="FOOD"
        private  val KEY_BILLS="BILLS"
        private  val KEY_SHOPPING="SHOPPING"
        private val KEY_DAILY="Daily_Needs"
        private  val KEY_OTHER="OTHERS"
        private val KEY_MONTHYEAR= "monthyear"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //p0?.execSQL("DROP TABLE "+ TABLE_TRANSACTIONS)

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AMOUNT +" INTEGER,"+ KEY_TYPE+" TEXT,"+ KEY_DAT+" TEXT,"+
                KEY_NOTE+" TEXT,"+ KEY_MODE+" TEXT,"+ KEY_TRANSACTIONID+" TEXT"+")")

       p0?.execSQL(CREATE_CONTACTS_TABLE)

        val CREATE_STATICS_TABLE = ("CREATE TABLE " + TABLE_Statics + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MONTHYEAR +" TEXT,"+ KEY_FOOD +" INTEGER,"+ KEY_BILLS +" INTEGER,"+
                 KEY_SHOPPING +" INTEGER,"+ KEY_DAILY +" INTEGER,"+
                 KEY_OTHER +" INTEGER"+ ")")
        p0?.execSQL(CREATE_STATICS_TABLE)


    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
       p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
       p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_Statics")
        onCreate(p0)
    }

    @SuppressLint("Range")
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

         // Closing database connection

        val ty= tran.Type.replace(
            " ",
            "_"
        )
         val db2= this.readableDatabase
        var cs: Cursor? = null
         cs= db2.rawQuery("SELECT "+ty+" FROM "+ TABLE_Statics+" WHERE "+ KEY_MONTHYEAR + " = '" +tran.Date.dropLast(3)+"'",null)
       if(cs.moveToFirst()){
           var amt=cs.getIntOrNull(cs.getColumnIndex(ty))
           if(amt==null){
             amt=0
           }
           amt=amt+tran.Amount
          db.execSQL("UPDATE "+ TABLE_Statics+" SET "+ ty +" = "+ amt +" WHERE "+ KEY_MONTHYEAR + " = '" +tran.Date.dropLast(3)+"'")
        //   return amt
       }else{
           val content= ContentValues()
           content.put(ty,tran.Amount)
           content.put(KEY_MONTHYEAR,tran.Date.dropLast(3))
           db.insert(TABLE_Statics,null,content)
       }
        db.close()
        db2.close()
     return success
    }

fun getMonthData(dat: LocalDate): typeModelClass {
val db= this.readableDatabase
    val stats: typeModelClass
    var cs: Cursor? = null
    cs= db.rawQuery("SELECT * FROM "+ TABLE_Statics+" WHERE "+ KEY_MONTHYEAR + " = '" + dat.toString().dropLast(3)+"'",null)
    if(cs.moveToFirst()){
      stats= typeModelClass(cs.getIntOrNull(cs.getColumnIndex(KEY_FOOD)),cs.getIntOrNull(cs.getColumnIndex(
          KEY_BILLS)),cs.getIntOrNull(cs.getColumnIndex(KEY_SHOPPING)),cs.getIntOrNull(cs.getColumnIndex(
          KEY_DAILY)),cs.getIntOrNull(cs.getColumnIndex(KEY_OTHER)))
    }else{
    stats= typeModelClass(0,0,0,0,0)
    }
    db.close()
    return stats
}
//    fun getRow(tran: finModelClass): finModelClass{
//      val db= this.readableDatabase
//
//    }
    }