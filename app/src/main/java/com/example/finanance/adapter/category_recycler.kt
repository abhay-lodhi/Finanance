package com.example.finanance.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finanance.R
import com.example.finanance.model.categoryModelClass


class category_recycler (private val mList: ArrayList<categoryModelClass>) : RecyclerView.Adapter<category_recycler.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_row, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList.get(position)
        holder.icon_category.setImageResource(ItemsViewModel.image)
        holder.amount.text = "\u20B9 "+"%,d".format(ItemsViewModel.Amount)
        holder.dte.text= ItemsViewModel.date


    }


    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val icon_category: ImageView = itemView.findViewById(R.id.icon_category)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val dte: TextView = itemView.findViewById(R.id.dte)
    }
}