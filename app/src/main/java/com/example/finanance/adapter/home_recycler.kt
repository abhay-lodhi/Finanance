package com.example.finanance.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.finanance.R
import com.example.finanance.model.homeRecyclerModelClass



class home_recycler (private val mList: ArrayList<homeRecyclerModelClass>) : RecyclerView.Adapter<home_recycler.ViewHolder>() {

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rvhomepage_row, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList.get(position)

        if(ItemsViewModel.Amount!=-1){
            holder.cont.layoutParams.height=70.px
            holder.monthYear.visibility=View.GONE
            holder.linear.visibility=View.VISIBLE
            holder.icon.setImageResource(ItemsViewModel.image)
            holder.category.text=ItemsViewModel.cat.toString()
            holder.amount.text = "\u20B9 "+"%,d".format(ItemsViewModel.Amount)
            holder.dte.text= ItemsViewModel.date
       }
        else{

            holder.monthYear.visibility=View.VISIBLE
            holder.linear.visibility=View.GONE
            holder.cont.layoutParams.height=50.px
            holder.monthYear.layoutParams.height= 50.px
            holder.monthYear.text=ItemsViewModel.cat
        }

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
        val monthYear: TextView= itemView.findViewById(R.id.monthyear)
        val linear: LinearLayout=itemView.findViewById(R.id.linearLayout)
        val cont: ConstraintLayout=itemView.findViewById(R.id.container)
    }
}

