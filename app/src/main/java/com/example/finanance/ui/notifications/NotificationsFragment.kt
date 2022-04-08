package com.example.finanance.ui.notifications


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finanance.DB.DBHandler
import com.example.finanance.R
import com.example.finanance.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    val currdate = "2022-04-01"
    var yearglob = Integer.parseInt(currdate[0].toString()+ currdate[1].toString()+currdate[2].toString()+currdate[3].toString())
    var monthglob =Integer.parseInt(currdate[5].toString()+ currdate[6].toString())

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

    //@RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)
        val datelocal:String
        if(monthglob<10){
            datelocal= yearglob.toString()+"-0"+monthglob.toString()+"-01"
        }else{
            datelocal = yearglob.toString()+"-"+monthglob.toString()+"-01"
        }
        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(datelocal)



        if(stats.FOOD==null){
            food?.text ="0"
        }else
            food?.text = stats.FOOD.toString()

        if(stats.BILLS==null){
            bills?.text = "0"
        }else
            bills?.text = stats.BILLS.toString()

        if(stats.SHOPPING==null){
            shopp?.text ="0"
        }else
            shopp?.text = stats.SHOPPING.toString()

        if(stats.Daily==null){
            daily?.text ="0"
        }else
            daily?.text = stats.Daily.toString()

        if(stats.OTHERS==null){
            others?.text = "0"
        }else
            others?.text = stats.OTHERS.toString()

        val searchmonth = getView()?.findViewById<EditText>(R.id.search)
        val enter= getView()?.findViewById<Button>(R.id.enter)
        val left = getView()?.findViewById<ImageView>(R.id.left_icon)
        val right = getView()?.findViewById<ImageView>(R.id.right_icon)

        enter?.setOnClickListener{ view ->
            search()
        }
        left?.setOnClickListener{ view ->
           before()
        }
        right?.setOnClickListener{ view ->
            next()
        }
    }

    fun search(){
       val searchmonth= getView()?.findViewById<EditText>(R.id.search)
        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)

        val months= mapOf("Jan" to "01","Feb" to "02","Mar" to "03","Apr" to "04","May" to "05","Jun" to "06","Jul" to "07","Aug" to "08","Sep" to "09","Oct" to "10","Nov" to "11","Dec" to "12")
        val monthyear: CharArray = searchmonth?.text.toString().trim().toCharArray()
        val size:Int= monthyear.size
        val mon = monthyear[0].toString()+ monthyear[1].toString() +monthyear[2].toString()
        val year = monthyear[size-4].toString()+ monthyear[size-3].toString()+monthyear[size-2].toString()+monthyear[size-1].toString()
        val month = months[mon]
        yearglob=Integer.parseInt(year)
        monthglob= Integer.parseInt(month)

        val databaseHandler: DBHandler = DBHandler(requireActivity().baseContext)
        val stats = databaseHandler.getMonthData(year+"-"+month+"-01")

        if(stats.FOOD==0&&stats.SHOPPING==0&&stats.BILLS==0&&stats.Daily==0&&stats.OTHERS==0){
            Toast.makeText(
                getActivity()?.getApplicationContext(),
                "No Data To show",
                Toast.LENGTH_LONG
            ).show()
        }else {
            if(stats.FOOD==null){
                food?.text ="0"
            }else
                food?.text = stats.FOOD.toString()

            if(stats.BILLS==null){
                bills?.text = "0"
            }else
                bills?.text = stats.BILLS.toString()

            if(stats.SHOPPING==null){
                shopp?.text ="0"
            }else
                shopp?.text = stats.SHOPPING.toString()

            if(stats.Daily==null){
                daily?.text ="0"
            }else
                daily?.text = stats.Daily.toString()

            if(stats.OTHERS==null){
                others?.text = "0"
            }else
                others?.text = stats.OTHERS.toString()

        }


    }



    //@RequiresApi(Build.VERSION_CODES.O)
    fun before(){
        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)

        val datelocal:String
        if(monthglob==1){
            --yearglob
            monthglob=12
        }else{
            --monthglob
        }
        if(monthglob<10){
            datelocal= yearglob.toString()+"-0"+monthglob.toString()+"-01"
        }else{
            datelocal = yearglob.toString()+"-"+monthglob.toString()+"-01"
        }
      // currentdate=currentdate.minusDays(30)

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
            //currentdate=currentdate.plusDays(30)
        }else {
            if(stats.FOOD==null){
                food?.text ="0"
            }else
                food?.text = stats.FOOD.toString()

            if(stats.BILLS==null){
                bills?.text = "0"
            }else
                bills?.text = stats.BILLS.toString()

            if(stats.SHOPPING==null){
                shopp?.text ="0"
            }else
                shopp?.text = stats.SHOPPING.toString()

            if(stats.Daily==null){
                daily?.text ="0"
            }else
                daily?.text = stats.Daily.toString()

            if(stats.OTHERS==null){
                others?.text = "0"
            }else
                others?.text = stats.OTHERS.toString()

        }
        }

//
//
//    }

    //@RequiresApi(Build.VERSION_CODES.O)
    fun next(){
        val food= getView()?.findViewById<TextView>(R.id.foodtxtamt)
        val bills=getView()?.findViewById<TextView>(R.id.billstxtamt)
        val shopp=getView()?.findViewById<TextView>(R.id.shoppingtxtamt)
        val daily=getView()?.findViewById<TextView>(R.id.dailytxtamt)
        val others=getView()?.findViewById<TextView>(R.id.otherstxtamt)
        val datelocal:String
        if(monthglob==12){
            ++yearglob
            monthglob=1
        }else{
            ++monthglob
        }
        if(monthglob<10){
        datelocal= yearglob.toString()+"-0"+monthglob.toString()+"-01"
        }else{
            datelocal = yearglob.toString()+"-"+monthglob.toString()+"-01"
        }

       // currentdate=currentdate.plusDays(30)

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
            if(stats.FOOD==null){
                food?.text ="0"
            }else
            food?.text = stats.FOOD.toString()

            if(stats.BILLS==null){
                bills?.text = "0"
            }else
            bills?.text = stats.BILLS.toString()

            if(stats.SHOPPING==null){
                shopp?.text ="0"
            }else
            shopp?.text = stats.SHOPPING.toString()

            if(stats.Daily==null){
                daily?.text ="0"
            }else
            daily?.text = stats.Daily.toString()

            if(stats.OTHERS==null){
                others?.text = "0"
            }else
            others?.text = stats.OTHERS.toString()

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}