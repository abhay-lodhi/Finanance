package com.example.finanance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomePageAdapter(val items:ArrayList<String>):RecyclerView.Adapter<Moneyviewholde>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Moneyviewholde {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.rvhomepage,parent,false)
        return Moneyviewholde(view)
    }

    override fun onBindViewHolder(holder: Moneyviewholde, position: Int) {
        val currentItem=items[position]
        holder.titleview.text=currentItem
        holder.titleviewt.text=currentItem


    }

    override fun getItemCount(): Int {
        return  items.size
    }
}
class Moneyviewholde(itemView: View): RecyclerView.ViewHolder(itemView){
    val titleview: TextView =itemView.findViewById(R.id.catrv)
    val titleviewt:TextView=itemView.findViewById(R.id.amountrv)
}