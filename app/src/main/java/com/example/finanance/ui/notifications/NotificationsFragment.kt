package com.example.finanance.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finanance.DB.DBHandler
import com.example.finanance.R
import com.example.finanance.databinding.FragmentNotificationsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
//


    @RequiresApi(Build.VERSION_CODES.O)
    val current: LocalDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    var currentdate = LocalDate.parse(current.format(formatter).toString().dropLast(2)+"05", DateTimeFormatter.ISO_DATE)


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(currentdate)



    food?.text = stats.FOOD.toString()
    bills?.text = stats.BILLS.toString()
    shopp?.text = stats.SHOPPING.toString()
    daily?.text = stats.Daily.toString()
    others?.text = stats.OTHERS.toString()



        val left = getView()?.findViewById<ImageView>(R.id.left_icon)
        val right = getView()?.findViewById<ImageView>(R.id.right_icon)

        left?.setOnClickListener{ view ->
           before()
        }
        right?.setOnClickListener{ view ->
            next()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun before(){
        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)

       currentdate=currentdate.minusDays(30)

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(currentdate)

        if(stats.FOOD==0&&stats.SHOPPING==0&&stats.BILLS==0&&stats.Daily==0&&stats.OTHERS==0){
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "No Data To show",
                Toast.LENGTH_LONG
            ).show()
            currentdate=currentdate.plusDays(30)
        }else {
            food?.text = stats.FOOD.toString()
            bills?.text = stats.BILLS.toString()
            shopp?.text = stats.SHOPPING.toString()
            daily?.text = stats.Daily.toString()
            others?.text = stats.OTHERS.toString()

        }
        }

//
//
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun next(){
        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)

        currentdate=currentdate.plusDays(30)

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(currentdate)

        if(stats.FOOD==0&&stats.SHOPPING==0&&stats.BILLS==0&&stats.Daily==0&&stats.OTHERS==0){
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "No Data To show",
                Toast.LENGTH_LONG
            ).show()
            currentdate=currentdate.minusDays(30)
        }else {
            food?.text = stats.FOOD.toString()
            bills?.text = stats.BILLS.toString()
            shopp?.text = stats.SHOPPING.toString()
            daily?.text = stats.Daily.toString()
            others?.text = stats.OTHERS.toString()

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}