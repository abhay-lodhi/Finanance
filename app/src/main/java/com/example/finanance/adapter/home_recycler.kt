package com.example.finanance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finanance.R
import com.example.finanance.model.homeRecyclerModelClass


class home_recycler (private val mList: ArrayList<homeRecyclerModelClass>) : RecyclerView.Adapter<home_recycler.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rvhomepage_row, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList.get(position)
        holder.icon.setImageResource(ItemsViewModel.image)
        holder.category.text=ItemsViewModel.cat.toString()
        holder.amount.text = ItemsViewModel.Amount.toString()
        holder.dte.text= ItemsViewModel.date

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val icon: ImageView = itemView.findViewById(R.id.IVrvhomepage)
        val amount: TextView = itemView.findViewById(R.id.amtrvhomepage)
        val dte: TextView = itemView.findViewById(R.id.datrvhomepage)
        val category: TextView= itemView.findViewById(R.id.catrvhomepage)
    }
}