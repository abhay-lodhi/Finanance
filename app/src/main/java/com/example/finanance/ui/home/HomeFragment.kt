package com.example.finanance.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finanance.HomePageAdapter
import com.example.finanance.R
import com.example.finanance.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
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

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        getView()?.findViewById<EditText>(R.id.amount)

//        val pieChart =getView()?.findViewById<PieChart>(R.id.homechart)
//        pieChart?.slices = listOf(
//            PieChart.Slice(0.5f, Color.BLACK),
//            PieChart.Slice(0.4f, Color.GRAY),
//            PieChart.Slice(0.05f, Color.YELLOW),
//            PieChart.Slice(0.05f, Color.RED)

        val sdf = SimpleDateFormat("EEE, MMMM dd, yyyy")
        val current = sdf.format(Date())

        tv.text = "$current"

        recyclerview.layoutManager= LinearLayoutManager(requireContext())

        val itemss=fetchdata()

        val adapter= HomePageAdapter(itemss)
        recyclerview.adapter=adapter
//        )
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }

    private fun fetchdata(): ArrayList<String>
    {
        val list=ArrayList<String>()
        for(i in 0..100)
        {
            list.add("Item $i")
        }
        return list
    }



}
