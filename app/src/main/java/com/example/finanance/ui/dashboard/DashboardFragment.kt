package com.example.finanance.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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
    val today = MaterialDatePicker.todayInUtcMilliseconds()

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
                val item= arrayadapter.getItem(position)
                when (item) {

                    "FOOD" -> {
                        textinput?.setStartIconDrawable(R.drawable.iconfood)
                    }
                    "BILLS" ->  textinput?.setStartIconDrawable(R.drawable.ic_outline_attach_money_24)
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
       // val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val save= getView()?.findViewById<Button>(R.id.Save)
        val datetext= getView()?.findViewById<EditText>(R.id.date)
      //  val delete= getView()?.findViewById<ImageView>(R.id.delete)



        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today
        calendar[Calendar.YEAR] = 2021
        val startDate = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.YEAR] = 2022
        val endDate = calendar.timeInMillis


        val builder = MaterialDatePicker.Builder.datePicker()

        val constraints: CalendarConstraints.Builder = CalendarConstraints.Builder()
        constraints.setStart(startDate)
        constraints.setEnd(endDate)



        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        builder.setCalendarConstraints(constraints.build())

        val datePick=  builder.build()

        datetext?.setText(sdf.format(today).toString())


        datetext?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePick.show(childFragmentManager, "tag")
                datePick.addOnPositiveButtonClickListener {
                    val dat = sdf.format(it)
                    datetext.setText(dat.toString())
                }
            }}

        datetext?.setOnClickListener{
            datePick.show(childFragmentManager, "tag")
            datePick.addOnPositiveButtonClickListener {
                // Respond to positive button click.
                //  date?.text=datePicker.getHeaderText()
                val dat = sdf.format(it)
                datetext.setText(dat.toString())

            }


        }
//delete?.setOnClickListener{ view ->
//    val databaseHandler = DBHandler(requireActivity().baseContext)
//    databaseHandler.cleardata()
//    Toast.makeText(getActivity()?.getApplicationContext(), "Records deleted", Toast.LENGTH_LONG).show()
//
//}
        save?.setOnClickListener{
            addRecord()
        }
    }

    fun addRecord(){
//        val amount= getView()?.findViewById<EditText>(R.id.amount)
//        val date= getView()?.findViewById<EditText>(R.id.date)
//        val notes= getView()?.findViewById<EditText>(R.id.addNotes)
//        val type =getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val amoun=binding.amount.text.toString()
        val addnote=binding.addNotes.text.toString()
        val dategiven= binding.date.text.toString()
        val typ= binding.autoCompleteTextView.text.toString()
        val mode="CASH"
        val transid= null

        val databaseHandler = DBHandler(requireActivity().baseContext)
        if (!amoun.isEmpty()) {
            val status = databaseHandler.addTransactions(finModelClass(0, Integer.parseInt(amoun), typ,dategiven,addnote,mode,transid))
            if (status > -1) {
                Toast.makeText(getActivity()?.getApplicationContext(), "Record saved", Toast.LENGTH_LONG).show()
                binding.amount.text.clear()
                binding.addNotes.text.clear()
               binding.date.setText(sdf.format(today).toString())
               binding.autoCompleteTextView.setText("FOOD")
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
                "Amount cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

