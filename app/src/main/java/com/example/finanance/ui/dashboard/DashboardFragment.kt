package com.example.finanance.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finanance.R
import com.example.finanance.databinding.FragmentDashboardBinding
import com.google.android.material.textfield.TextInputLayout


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onResume() {
        super.onResume()
        val category=resources.getStringArray(R.array.category)
        val arrayadapter=ArrayAdapter(requireContext(),R.layout.dropdown_category,category)
        binding.autoCompleteTextView.setAdapter(arrayadapter)

        val t2 =getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val textinput =getView()?.findViewById<TextInputLayout>(R.id.textinputlayout)

        t2?.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
               val t3= arrayadapter?.getItem(position)
                when (t3) {
                    "FOOD" -> {
                        textinput?.setStartIconDrawable(R.drawable.ic_outline_fastfood_24)
                    }
                    "BILLS" ->  textinput?.setStartIconDrawable(R.drawable.ic_outline_attach_money_24)
                    "CLOTHING" -> textinput?.setStartIconDrawable(R.drawable.ic_outline_attach_money_24)
                    else -> { // Note the block
                        print("x is neither 1 nor 2")
                    }
                }

            }}
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}