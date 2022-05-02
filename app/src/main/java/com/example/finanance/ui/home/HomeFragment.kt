package com.example.finanance.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finanance.DB.DBHandler
import com.example.finanance.R
import com.example.finanance.adapter.home_recycler
import com.example.finanance.databinding.FragmentHomeBinding
import com.example.finanance.model.homeRecyclerModelClass
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var pieChart: PieChart
 var food =0f
    var bills=0f
    var shopp=0f
    var daily=0f
    var other =0f
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("EEE, MMMM dd, yyyy")
        val current = sdf.format(Date())
        val tV= getView()?.findViewById<TextView>(R.id.tv)
        tV?.text = "$current"


        val recyclerview = getView()?.findViewById<RecyclerView>(R.id.rvhome)
         val tvrecords = getView()?.findViewById<TextView>(R.id.tvNoRecordsAvailable)
        if (getItemsList().size > 0) {

            recyclerview?.visibility = View.VISIBLE
             tvrecords?.visibility = View.GONE
          //  tvrecors?.text="heyyy"
            // Set the LayoutManager that this RecyclerView will use.
            recyclerview?.layoutManager = LinearLayoutManager(requireContext())
            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = home_recycler(getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.

            recyclerview?.adapter = itemAdapter

        } else {
          //  tvrecors?.text="heyyy1"
            recyclerview?.visibility = View.GONE
            tvrecords?.visibility = View.VISIBLE
        }


//        recyclerview?.layoutManager = LinearLayoutManager(requireContext())
//        val databaseHandler: DBHandler = DBHandler(requireContext())
//        val catList: ArrayList<homeRecyclerModelClass> = databaseHandler.getdetails()
//        val  catAdapter = home_recycler(catList)
//        recyclerview?.adapter= catAdapter

        val sdf1 = SimpleDateFormat("dd/MM/yyyy")
        val current1 = sdf1.format(Date())

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(current1)
        pieChart = requireView()!!.findViewById<PieChart>(R.id.pieChart)



        if(stats.FOOD==null){
           food=0f
        }else
          food = stats.FOOD!!.toFloat()

        if(stats.BILLS==null){
            bills=0f
        }else
            bills=stats.BILLS!!.toFloat()

        if(stats.SHOPPING==null){
          shopp=0f
        }else
          shopp=stats.SHOPPING!!.toFloat()

        if(stats.Daily==null){
           daily=0f
        }else
          daily=stats.Daily!!.toFloat()

        if(stats.OTHERS==null){
           other=0f
        }else
            other=stats.OTHERS!!.toFloat()
        initPieChart()

        setDataToPieChart()
    }
    private fun initPieChart() {
        pieChart.setUsePercentValues(false)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true

    }


    private fun setDataToPieChart() {
        pieChart.setUsePercentValues(false)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(food, "Food"))
        dataEntries.add(PieEntry(bills, "Bills"))
        dataEntries.add(PieEntry(shopp, "Shopping"))
        dataEntries.add(PieEntry(daily, "Daily Needs"))
        dataEntries.add(PieEntry(other, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#db3236"))
        colors.add(Color.parseColor("#f4c20d"))
        colors.add(Color.parseColor("#4885ed"))
        colors.add(Color.parseColor("#FF9800"))
        colors.add(Color.parseColor("#3cba54"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 0f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)




        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "This Month= "+(food+bills+shopp+daily+other).toInt().toString()




        pieChart.invalidate()

    }

    private fun getItemsList(): ArrayList<homeRecyclerModelClass> {
        //creating the instance of DatabaseHandler class
        val databaseHandler: DBHandler = DBHandler(requireContext())
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val empList: ArrayList<homeRecyclerModelClass> = databaseHandler.getdetails()

        return empList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}