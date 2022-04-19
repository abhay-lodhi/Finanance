package com.example.finanance.ui.home

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
import com.example.finanance.adapter.category_recycler
import com.example.finanance.adapter.home_recycler
import com.example.finanance.databinding.FragmentHomeBinding
import com.example.finanance.model.categoryModelClass
import com.example.finanance.model.homeRecyclerModelClass
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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