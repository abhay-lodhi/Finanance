package com.example.finanance.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getIntOrNull
import com.example.finanance.R
import com.example.finanance.model.categoryModelClass
import com.example.finanance.model.finModelClass
import com.example.finanance.model.homeRecyclerModelClass
import com.example.finanance.model.typeModelClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
         cs= db2.rawQuery("SELECT "+ty+" FROM "+ TABLE_Statics+" WHERE "+ KEY_MONTHYEAR + " = '" +tran.Date.drop(3)+"'",null)
       if(cs.moveToFirst()){
           var amt=cs.getIntOrNull(cs.getColumnIndex(ty))
           if(amt==null){
             amt=0
           }
           amt=amt+tran.Amount
          db.execSQL("UPDATE "+ TABLE_Statics+" SET "+ ty +" = "+ amt +" WHERE "+ KEY_MONTHYEAR + " = '" +tran.Date.drop(3)+"'")
        //   return amt
       }else{
           val content= ContentValues()
           content.put(ty,tran.Amount)
           content.put(KEY_MONTHYEAR,tran.Date.drop(3))
           db.insert(TABLE_Statics,null,content)
       }
        db.close()
        db2.close()
     return success
    }

fun getMonthData(dat: String): typeModelClass {
val db= this.readableDatabase
    val stats: typeModelClass
    var cs: Cursor? = null
    cs= db.rawQuery("SELECT * FROM "+ TABLE_Statics+" WHERE "+ KEY_MONTHYEAR + " = '" + dat.toString().drop(3)+"'",null)
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

    @SuppressLint("Range")
    fun getMonthdetails(dat: String, cate: String, img : Int): ArrayList<categoryModelClass>{
        val catList: ArrayList<categoryModelClass> = ArrayList<categoryModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM "+ TABLE_TRANSACTIONS+" WHERE "+ KEY_TYPE+" = '"+cate+"' AND " + KEY_DAT +" LIKE '"+dat+"' ORDER BY "+ KEY_DAT+" DESC"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var amount: Int
        var dat: String

        if (cursor.moveToFirst()) {
            do {
                id =cursor.getInt(cursor.getColumnIndex(KEY_ID))
                amount = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT))
                dat = cursor.getString(cursor.getColumnIndex(KEY_DAT))

                val cat = categoryModelClass(id = id, image=img , Amount = amount,date= dat )
                catList.add(cat)
            } while (cursor.moveToNext())
        }
        return catList

    }

    @SuppressLint("Range")
    fun getdetails(): ArrayList<homeRecyclerModelClass>{
        val catList: ArrayList<homeRecyclerModelClass> = ArrayList<homeRecyclerModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM "+ TABLE_TRANSACTIONS +" ORDER BY "+ KEY_DAT+" DESC"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var amount: Int
        var dat: String
        var cat: String
        var img: Int= R.drawable.ic_outline_attach_money_24

        if (cursor.moveToFirst()) {
            do {
                id =cursor.getInt(cursor.getColumnIndex(KEY_ID))
                cat=cursor.getString(cursor.getColumnIndex(KEY_TYPE))
                amount = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT))
                dat = cursor.getString(cursor.getColumnIndex(KEY_DAT))
                val format1 = SimpleDateFormat("dd/MM/yyyy")
                val format2 = SimpleDateFormat("dd-MMM-yyyy")
                val date: Date = format1.parse(dat)
                dat= format2.format(date)

                when (cat) {
                    "FOOD" -> img= R.drawable.ic_outline_fastfood_24
                    "BILLS" ->  img= R.drawable.ic_outline_attach_money_24
                    "SHOPPING" -> img= R.drawable.ic_dashboard_black_24dp
                    "Daily Needs"-> img= R.drawable.ic_baseline_chevron_right_24
                    "OTHERS"-> img= R.drawable.ic_outline_info_24

                    else -> { // Note the block
                        print("x is neither 1 nor 2")
                    }
                }

                val row = homeRecyclerModelClass(id = id, cat=cat, image=img , Amount = amount,date= dat )
                catList.add(row)
            } while (cursor.moveToNext())
        }
        return catList

    }


//    fun getRow(tran: finModelClass): finModelClass{
//      val db= this.readableDatabase
//
//    }
    }