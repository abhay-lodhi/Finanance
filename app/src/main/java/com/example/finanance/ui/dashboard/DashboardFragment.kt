package com.example.finanance.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finanance.DB.DBHandler
import com.example.finanance.R
import com.example.finanance.databinding.FragmentDashboardBinding
import com.example.finanance.model.finModelClass
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


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

        val type =getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val textinput =getView()?.findViewById<TextInputLayout>(R.id.textinputlayout)
        type?.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val item= arrayadapter?.getItem(position)
                when (item) {
                    "FOOD" -> {
                        textinput?.setStartIconDrawable(R.drawable.iconfood)
                    }
                    "BILLS" ->  textinput?.setStartIconDrawable(R.drawable.ic_outline_currency_rupee_24)
                    "SHOPPING"  -> textinput?.setStartIconDrawable(R.drawable.shoping)
                    "Daily Needs"  -> textinput?.setStartIconDrawable(R.drawable.icone)
                    "OTHERS"  -> textinput?.setStartIconDrawable(R.drawable.icongift)
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
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datepick = getView()?.findViewById<EditText>(R.id.date)
        val save= getView()?.findViewById<Button>(R.id.Save)
        val datetext= getView()?.findViewById<EditText>(R.id.date)

        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today
        calendar[Calendar.YEAR] = 2021
        val startDate = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.YEAR] = 2023
        val endDate = calendar.timeInMillis


        val builder = MaterialDatePicker.Builder.datePicker()
        val constraints: CalendarConstraints.Builder = CalendarConstraints.Builder()
        constraints.setStart(startDate)
        constraints.setEnd(endDate)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        builder.setCalendarConstraints(constraints.build())

        val datePicker= builder.build()

        datepick?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePicker.show(childFragmentManager, "tag");
                datePicker.addOnPositiveButtonClickListener {
                    val dat = sdf.format(it)
                    datetext?.setText(dat.toString())
                }
            }}
        datepick?.setOnClickListener{view ->
            datePicker.show(childFragmentManager, "tag");
            datePicker.addOnPositiveButtonClickListener {
                // Respond to positive button click.
                //  date?.text=datePicker.getHeaderText()
                val dat = sdf.format(it)
                datetext?.setText(dat.toString())

            }


        }

        save?.setOnClickListener{ view ->
            addRecord()
        }
    }

    fun addRecord(){
        val amount= getView()?.findViewById<EditText>(R.id.amount)
        val date= getView()?.findViewById<EditText>(R.id.date)
        val notes= getView()?.findViewById<EditText>(R.id.addNotes)
        val type =getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val amoun=amount?.text.toString()
        val addnote=notes?.text.toString()
        val dategiven= date?.text.toString()
        val typ= type?.text.toString()
        val mode="CASH"
        val transid= null

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        if (!amoun.isEmpty() && !dategiven.isEmpty() && type!=null) {
            val status = databaseHandler.addTransactions(finModelClass(0, Integer.parseInt(amoun), typ,dategiven,addnote,mode,transid))
            if (status > -1) {
                Toast.makeText(getActivity()?.getApplicationContext(), "Record saved", Toast.LENGTH_LONG).show()
                amount?.text?.clear()
                notes?.text?.clear()
                date?.text?.clear()
                type?.text?.clear()
            }else{
                Toast.makeText(
                    getActivity()?.getApplicationContext(),
                    "Error"+status,
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "Fields cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

