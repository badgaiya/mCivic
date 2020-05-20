package manacle.mcivic.ui.login

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_helper_data_form.*
import kotlinx.android.synthetic.main.activity_visitors_data.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import manacle.mcivic.R
 import manacle.mcivic.adapter.MyViewPageStateAdapter
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.common.Constants
import manacle.mcivic.data.model.RecurrindList
import manacle.mcivic.data.model.SpinnerListGender
import manacle.mcivic.data.model.SpinnerListId
import manacle.mcivic.data.model.VechileTypeLiist
import manacle.mcivic.fragments.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

class VisitorsDataActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var progerssProgressDialog: ProgressDialog
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)

    lateinit var recurrindList: ArrayList<RecurrindList>
    lateinit private var vechileTypeLiist: ArrayList<VechileTypeLiist>


    var Recurring_dropdown: String = ""
    var Vehicle_Type_dropdown: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitors_data)
        initViews()
       // setStatePageAdapter()

       /* tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })*/
    }

    private fun initViews() {
        getSpinnerList()

        layout_save.setOnClickListener({
            submitData()

        })
      /*  tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)*/
    }

    private fun setStatePageAdapter() {
        val myViewPageStateAdapter: MyViewPageStateAdapter =
            MyViewPageStateAdapter(supportFragmentManager)
        myViewPageStateAdapter.addFragment(GuestFragment(), "Guest")
        myViewPageStateAdapter.addFragment(CabFragment(), "Cab")
        myViewPageStateAdapter.addFragment(DeliveryFragment(), "Delivery")
        myViewPageStateAdapter.addFragment(OtherFragment(), "Other")
        viewPager.adapter = myViewPageStateAdapter
        tabLayout.setupWithViewPager(viewPager, true)

    }




    private fun submitData() {
        val visitor_mobile: String = visitor_mobile.text.toString()
        val visitor_name: String = visitor_name.text.toString()
        val vehicle_number: String = vehicle_number.text.toString()
        val visitor_scedule: String = visitor_scedule.text.toString()

        try {
            progerssProgressDialog = ProgressDialog(this)
            progerssProgressDialog.setTitle("Please wait for proceeding!!")
            progerssProgressDialog.setCancelable(false)
            progerssProgressDialog.show()
            val service = ApiClient.getClient().create(ApiInterface::class.java)
            val call = service.sendVisitorDataSubmit(
                "37",
                "0",
                visitor_mobile,
                visitor_name,
                Constants.getDate(),
                Recurring_dropdown,
                Vehicle_Type_dropdown,
                vehicle_number
                )
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    try {
                        if (response != null) {
                            if (response.code() == 200) {
                                Log.d("asdfghj", response.toString())
                                progerssProgressDialog.dismiss()


                              /*  intent = Intent(this@VisitorsDataActivity, MyHelper::class.java)

                                startActivity(intent)*/
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    progerssProgressDialog.dismiss()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clickDataPicker(view: View) {


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in Toast
            visitor_scedule.setText("""$dayOfMonth - ${monthOfYear + 1} - $year""")

            //Toast.makeText(this, , Toast.LENGTH_LONG).show()

        }, year, month, day)
        dpd.show()
    }

    private fun getSpinnerList() {
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.spinnerDataList("37")
        call.enqueue(object : Callback<JsonObject> {
            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                try {
                    if (response != null) {
                        if (response.code() == 200) {
                            Log.d("response____________", response.toString())

                            recurrindList = ArrayList()
                            recurrindList.clear()
                            vechileTypeLiist = ArrayList()
                            vechileTypeLiist.clear()
                            val mResponseBody: JsonObject = response.body()!!
                            val responseStatus: Boolean =
                                mResponseBody.get("response").getAsBoolean()
                            if (responseStatus) {
                                val recurring_dropdown_mode: JsonArray =
                                    mResponseBody.getAsJsonObject()
                                        .getAsJsonArray("visitor_staying_period")
                                if (recurring_dropdown_mode.size() > 0) {
                                    for (j in 0 until recurring_dropdown_mode!!.size()) {
                                        val id: String =
                                            recurring_dropdown_mode.get(j)
                                                .asJsonObject.get("id").asString
                                        val name: String =
                                            recurring_dropdown_mode.get(j)
                                                .asJsonObject.get("name").asString
                                        var childItem = RecurrindList(
                                            id,
                                            name
                                        )
                                        recurrindList.add(childItem)
                                    }
                                }
                                val spinnerArray = arrayOfNulls<String>(recurrindList.size)
                                val spinnerMap: HashMap<Int, String> = HashMap<Int, String>();
                                for (i in 0 until recurrindList.size) {
                                    spinnerMap.put(i, recurrindList.get(i).id);
                                    spinnerArray[i] = recurrindList.get(i).name;
                                }
                                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                    this@VisitorsDataActivity, android.R.layout.simple_spinner_item,
                                    spinnerArray
                                );
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                single_visit.setAdapter(adapter);
                                single_visit.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            arg0: AdapterView<*>?,
                                            arg1: View,
                                            arg2: Int,
                                            arg3: Long
                                        ) {
                                            Recurring_dropdown = spinnerMap.get(single_visit.getSelectedItemPosition())
                                                .toString();
                                        }
                                        override fun onNothingSelected(arg0: AdapterView<*>?) {}
                                    }
                                val Vehicle_Type_dropdown_mode: JsonArray =
                                    mResponseBody.getAsJsonObject()
                                        .getAsJsonArray("vehicle_details")
                                if (Vehicle_Type_dropdown_mode.size() > 0) {
                                    for (j in 0 until Vehicle_Type_dropdown_mode!!.size()) {
                                        val id: String =
                                            Vehicle_Type_dropdown_mode.get(j)
                                                .asJsonObject.get("id").asString
                                        val name: String =
                                            Vehicle_Type_dropdown_mode.get(j)
                                                .asJsonObject.get("name").asString
                                        var childItem = VechileTypeLiist(
                                            id,
                                            name
                                        )
                                        vechileTypeLiist.add(childItem)

                                    }
                                    val spinnerArrayy =
                                        arrayOfNulls<String>(vechileTypeLiist.size)

                                    val spinnerMapp: HashMap<Int, String> = HashMap<Int, String>();
                                    for (i in 0 until vechileTypeLiist.size) {
                                        spinnerMapp.put(i, vechileTypeLiist.get(i).id);
                                        spinnerArrayy[i] = vechileTypeLiist.get(i).name;
                                    }
                                    val adapterr: ArrayAdapter<String> =
                                        ArrayAdapter<String>(
                                            this@VisitorsDataActivity,
                                            android.R.layout.simple_spinner_item,
                                            spinnerArrayy
                                        );
                                    adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    vehile_type_spinner.setAdapter(adapterr);
                                    vehile_type_spinner.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                arg0: AdapterView<*>?,
                                                arg1: View,
                                                arg2: Int,
                                                arg3: Long
                                            ) {
                                                Vehicle_Type_dropdown =
                                                    spinnerMapp.get(vehile_type_spinner.getSelectedItemPosition())
                                                        .toString();
                                            }

                                            override fun onNothingSelected(arg0: AdapterView<*>?) {}
                                        }

                                } else {

                                }


                            }



                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()


                }


            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
            }

        })
    }



}







