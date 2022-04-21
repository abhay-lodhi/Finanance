package com.example.finanance.ui.notifications



import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finanance.DB.DBHandler
import com.example.finanance.R
import com.example.finanance.adapter.category_recycler
import com.example.finanance.databinding.FragmentNotificationsBinding
import com.example.finanance.model.categoryModelClass
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.SimpleDateFormat
import java.util.*


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var catAdapter: category_recycler
    private lateinit var dialog: Dialog
    private lateinit var pieChart: PieChart
    var food =0f
    var bills=0f
    var shopp=0f
    var daily=0f
    var other =0f


    val cur = Calendar.getInstance().time
    val df = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
    val currdate = df.format(cur)
    val monthstodigit= mapOf("Jan" to "01","Feb" to "02","Mar" to "03","Apr" to "04","May" to "05","Jun" to "06","Jul" to "07","Aug" to "08","Sep" to "09","Oct" to "10","Nov" to "11","Dec" to "12")
    val digittomonths = mapOf(1 to "Jan", 2 to "Feb", 3 to "Mar", 4 to "Apr", 5 to "May", 6 to "Jun", 7 to "Jul", 8 to "Aug", 9 to "Sep", 10 to "Oct", 11 to "Nov", 12 to "Dec")
    val digittomonths2 = mapOf(1 to "01", 2 to "02", 3 to "03", 4 to "04", 5 to "05", 6 to "06", 7 to "07", 8 to "08", 9 to "09", 10 to "10", 11 to "11", 12 to "12")

    var yearglob = Integer.parseInt(currdate[6].toString()+ currdate[7].toString()+currdate[8].toString()+currdate[9].toString())
    var monthglob =Integer.parseInt(currdate[3].toString()+ currdate[4].toString())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        binding.search.setText(digittomonths[monthglob].toString()+" ,"+ yearglob.toString())
    }

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = requireView()!!.findViewById<PieChart>(R.id.pieChart)

        val datelocal:String
        if(monthglob<10){
            datelocal="01/0" +monthglob.toString()+"/"+yearglob.toString()
        }else{
            datelocal = "01/" +monthglob.toString()+"/"+yearglob.toString()
        }

    //   binding.search.setText( digittomonths[monthglob].toString()+" ,"+ yearglob.toString())

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(datelocal)

        if(stats.FOOD==null){
           binding.foodtxtamt.setText("0")
            food=0f
        }else{
            binding.foodtxtamt.setText(stats.FOOD.toString())
        food=stats.FOOD!!.toFloat()
        }

        if(stats.BILLS==null){
            binding.billstxtamt.setText("0")
            bills=0f
        }else{
            binding.billstxtamt.setText(stats.BILLS.toString())
            bills=stats.BILLS!!.toFloat()
        }

        if(stats.SHOPPING==null){
            binding.shoppingtxtamt.setText("0")
            shopp=0f
        }else{
            binding.shoppingtxtamt.setText(stats.SHOPPING.toString())
        shopp=stats.SHOPPING!!.toFloat()
        }

        if(stats.Daily==null){
            binding.dailytxtamt.setText("0")
            daily=0f
        }else{
            binding.dailytxtamt.setText(stats.Daily.toString())
        daily=stats.Daily!!.toFloat()
        }

        if(stats.OTHERS==null){
            binding.otherstxtamt.setText("0")
            other= 0f
        }else{
            binding.otherstxtamt.setText(stats.OTHERS.toString())
        other=stats.OTHERS!!.toFloat()
        }
        initPieChart()

        setDataToPieChart()

       val searchmonth = getView()?.findViewById<EditText>(R.id.search)
        val left = getView()?.findViewById<ImageView>(R.id.left_icon)
        val right = getView()?.findViewById<ImageView>(R.id.right_icon)
        val food= getView()?.findViewById<TextView>(R.id.foodtext)
        val bills= getView()?.findViewById<TextView>(R.id.billstext)
        val shopp= getView()?.findViewById<TextView>(R.id.shoppingtext)
        val daily= getView()?.findViewById<TextView>(R.id.dailytext)
        val others= getView()?.findViewById<TextView>(R.id.otherstext)

        searchmonth?.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                return@OnEditorActionListener true
            }
            false
        })

        initPieChart()

        setDataToPieChart()

        food?.setOnClickListener{ view ->
            dialog("FOOD",R.drawable.iconfood)
        }
        bills?.setOnClickListener{ view ->
            dialog("BILLS",R.drawable.ic_outline_attach_money_24)
        }
        shopp?.setOnClickListener{ view ->
            dialog("SHOPPING",R.drawable.shoping)
        }
        daily?.setOnClickListener{ view ->
            dialog("Daily Needs",R.drawable.icone)
        }
        others?.setOnClickListener{ view ->
            dialog("OTHERS",R.drawable.icongift)
        }
        left?.setOnClickListener{ view ->
           before()
        }
        right?.setOnClickListener{ view ->
            next()
        }
    }

    fun dialog(type: String, img: Int){
        this.dialog = Dialog(requireContext())
        this.dialog.setContentView(R.layout.category_dialog)
        val recyclerview = this.dialog.findViewById<RecyclerView>(R.id.recycler_cat)
        val tvrecords = this.dialog.findViewById<TextView>(R.id.tvNoRecordsAvailable)
        if (getItemsList(type, img).size > 0) {

            recyclerview?.visibility = View.VISIBLE
            tvrecords?.visibility = View.GONE


            // Set the LayoutManager that this RecyclerView will use.
            recyclerview?.layoutManager = LinearLayoutManager(requireContext())
            // Adapter class is initialized and list is passed in the param.
            this.catAdapter = category_recycler(getItemsList(type,img))
            // adapter instance is set to the recyclerview to inflate the items.

            recyclerview?.adapter = this.catAdapter

        } else {

            recyclerview?.visibility = View.GONE
            tvrecords?.visibility = View.VISIBLE
        }
       // val recyclerview = this.dialog.findViewById<RecyclerView>(R.id.recycler_cat)
//        recyclerview?.layoutManager = LinearLayoutManager(requireContext())
//        val databaseHandler: DBHandler = DBHandler(requireContext())
//        val catList: ArrayList<categoryModelClass> = databaseHandler.getMonthdetails("%"+monthglob.toString()+"/"+yearglob.toString(), type,img) //     this.catAdapter = category_recycler(catList)
//        recyclerview?.adapter= this.catAdapter
        dialog.show()
    }


    private fun getItemsList(type: String, img: Int): ArrayList<categoryModelClass> {
        //creating the instance of DatabaseHandler class
        val databaseHandler: DBHandler = DBHandler(requireContext())
        val catList: ArrayList<categoryModelClass> = databaseHandler.getMonthdetails(yearglob.toString()+"-"+digittomonths2[monthglob]+"-%", type , img )

        return catList
    }


    fun search(){
       val searchmonth= getView()?.findViewById<EditText>(R.id.search)
        val monthyear: CharArray = searchmonth?.text.toString().trim().toCharArray()
        val size:Int= monthyear.size
        val mon = monthyear[0].toString()+ monthyear[1].toString() +monthyear[2].toString()
        val year = monthyear[size-4].toString()+ monthyear[size-3].toString()+monthyear[size-2].toString()+monthyear[size-1].toString()
        val month = monthstodigit[mon]



        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData("01/"+month+"/"+year)

        if(stats.FOOD==0&&stats.SHOPPING==0&&stats.BILLS==0&&stats.Daily==0&&stats.OTHERS==0){
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "No Data To show",
                Toast.LENGTH_LONG
            ).show()
             binding.search.setText(digittomonths[monthglob]+" ,"+yearglob)
        }else {
            yearglob=Integer.parseInt(year)
            monthglob= Integer.parseInt(month)

            if(stats.FOOD==null){
                binding.foodtxtamt.setText("0")
                food=0f
            }else{
                binding.foodtxtamt.setText(stats.FOOD.toString())
                food=stats.FOOD!!.toFloat()
            }

            if(stats.BILLS==null){
                binding.billstxtamt.setText("0")
                bills=0f
            }else{
                binding.billstxtamt.setText(stats.BILLS.toString())
                bills=stats.BILLS!!.toFloat()
            }

            if(stats.SHOPPING==null){
                binding.shoppingtxtamt.setText("0")
                shopp=0f
            }else{
                binding.shoppingtxtamt.setText(stats.SHOPPING.toString())
                shopp=stats.SHOPPING!!.toFloat()
            }

            if(stats.Daily==null){
                binding.dailytxtamt.setText("0")
                daily=0f
            }else{
                binding.dailytxtamt.setText(stats.Daily.toString())
                daily=stats.Daily!!.toFloat()
            }

            if(stats.OTHERS==null){
                binding.otherstxtamt.setText("0")
                other= 0f
            }else{
                binding.otherstxtamt.setText(stats.OTHERS.toString())
                other=stats.OTHERS!!.toFloat()
            }
            initPieChart()

            setDataToPieChart()
        }

        val `in`: InputMethodManager? =
            getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        `in`?.hideSoftInputFromWindow(searchmonth?.getWindowToken(), 0)
    }


    fun before(){
        val datelocal:String
        if(monthglob==1){
            --yearglob
            monthglob=12
        }else{
            --monthglob
        }
        if(monthglob<10){
            datelocal="01/0" +monthglob.toString()+"/"+yearglob.toString()
        }else{
            datelocal = "01/" +monthglob.toString()+"/"+yearglob.toString()
        }

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(datelocal)

        if(stats.FOOD==0&&stats.SHOPPING==0&&stats.BILLS==0&&stats.Daily==0&&stats.OTHERS==0){
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "No Data To show",
                Toast.LENGTH_LONG
            ).show()
            if(monthglob==12){
                ++yearglob
                monthglob=1
            }else{
                ++monthglob
            }
        }else {
            binding.search.setText(digittomonths[monthglob]+" ,"+yearglob)
            if(stats.FOOD==null){
                binding.foodtxtamt.setText("0")
                food=0f
            }else{
                binding.foodtxtamt.setText(stats.FOOD.toString())
                food=stats.FOOD!!.toFloat()
            }

            if(stats.BILLS==null){
                binding.billstxtamt.setText("0")
                bills=0f
            }else{
                binding.billstxtamt.setText(stats.BILLS.toString())
                bills=stats.BILLS!!.toFloat()
            }

            if(stats.SHOPPING==null){
                binding.shoppingtxtamt.setText("0")
                shopp=0f
            }else{
                binding.shoppingtxtamt.setText(stats.SHOPPING.toString())
                shopp=stats.SHOPPING!!.toFloat()
            }

            if(stats.Daily==null){
                binding.dailytxtamt.setText("0")
                daily=0f
            }else{
                binding.dailytxtamt.setText(stats.Daily.toString())
                daily=stats.Daily!!.toFloat()
            }

            if(stats.OTHERS==null){
                binding.otherstxtamt.setText("0")
                other= 0f
            }else{
                binding.otherstxtamt.setText(stats.OTHERS.toString())
                other=stats.OTHERS!!.toFloat()
            }
            initPieChart()

            setDataToPieChart()

        }
        }


    fun next(){
        val datelocal:String

        if(monthglob==12){
            ++yearglob
            monthglob=1
        }else{
            ++monthglob
        }

        if(monthglob<10){
            datelocal="01/0" +monthglob.toString()+"/"+yearglob.toString()
        }else{
            datelocal = "01/" +monthglob.toString()+"/"+yearglob.toString()
        }


        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(datelocal)

        if(stats.FOOD==0&&stats.SHOPPING==0&&stats.BILLS==0&&stats.Daily==0&&stats.OTHERS==0){
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "No Data To show",
                Toast.LENGTH_LONG
            ).show()
            if(monthglob==1){
                --yearglob
                monthglob=12
            }else{
                --monthglob
            }

        }else {
            binding.search.setText(digittomonths[monthglob]+" ,"+yearglob)
            if(stats.FOOD==null){
                binding.foodtxtamt.setText("0")
                food=0f
            }else{
                binding.foodtxtamt.setText(stats.FOOD.toString())
                food=stats.FOOD!!.toFloat()
            }

            if(stats.BILLS==null){
                binding.billstxtamt.setText("0")
                bills=0f
            }else{
                binding.billstxtamt.setText(stats.BILLS.toString())
                bills=stats.BILLS!!.toFloat()
            }

            if(stats.SHOPPING==null){
                binding.shoppingtxtamt.setText("0")
                shopp=0f
            }else{
                binding.shoppingtxtamt.setText(stats.SHOPPING.toString())
                shopp=stats.SHOPPING!!.toFloat()
            }

            if(stats.Daily==null){
                binding.dailytxtamt.setText("0")
                daily=0f
            }else{
                binding.dailytxtamt.setText(stats.Daily.toString())
                daily=stats.Daily!!.toFloat()
            }

            if(stats.OTHERS==null){
                binding.otherstxtamt.setText("0")
                other= 0f
            }else{
                binding.otherstxtamt.setText(stats.OTHERS.toString())
                other=stats.OTHERS!!.toFloat()
            }
            initPieChart()

            setDataToPieChart()

        }

    }

    private fun initPieChart() {
        pieChart.setUsePercentValues(true)
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
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Total Amount= "+(food+bills+shopp+daily+other).toInt().toString()



        pieChart.invalidate()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
