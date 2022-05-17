package com.pulk.finanance.model


class finModelClass(val id: Int, val Amount: Int, val Type: String,var Date: String , val Note: String,val Mode: String,val Transaction_id : String?)
class typeModelClass(val FOOD: Int?, val BILLS: Int?, val SHOPPING: Int?, val Daily: Int?, val OTHERS: Int?)
class categoryModelClass(val id: Int , val image: Int, val Amount: Int , val date: String)
class homeRecyclerModelClass(val id: Int,val cat: String, val image: Int, val Amount: Int, val date: String)