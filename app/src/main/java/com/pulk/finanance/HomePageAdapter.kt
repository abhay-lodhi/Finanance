package com.pulk.finanance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finanance.R

class HomePageAdapter(val items:ArrayList<String>): RecyclerView.Adapter<Newsviewholde>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Newsviewholde {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.rvhomepage,parent,false)
        return Newsviewholde(view)
    }
    override fun onBindViewHolder(holder: Newsviewholde, position: Int) {
        val currentItem=items[position]
        holder.titleview.text=currentItem
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}
class Newsviewholde(itemView: View): RecyclerView.ViewHolder(itemView){
    val titleview:TextView=itemView.findViewById(R.id.textView)

}