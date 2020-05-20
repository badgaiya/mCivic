package manacle.mcivic.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_helper_data_list_form.*
import manacle.mcivic.R
import manacle.mcivic.adapter.MyHelperDataListAdapter
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.data.model.MyHelperDataModel
import manacle.mcivic.database.CivicRoomDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HelperDataListForm : AppCompatActivity() {

    private var db: CivicRoomDatabase? = null
    lateinit var recyclerView: RecyclerView
    private var myHelperdataModel: ArrayList<MyHelperDataModel> = ArrayList()
    //lateinit var context:Context

    var company_id: String = ""

    lateinit var progerssProgressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper_data_list_form)
        recyclerView = findViewById(R.id.recyclerViewMyhelper) as RecyclerView
        getDataList()
        fab.setOnClickListener({
            val intent= Intent(this,HelperDataForm::class.java)
            startActivity(intent)

        })

    }

    private fun getDataList() {
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Data is loading.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        //myHelperModel.clear()
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.myHelpergetDataList("37", "0")

        call.enqueue(object : Callback<JsonObject> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                try {
                    if (response != null) {
                        if (response.code() == 200) {
                            myHelperdataModel = ArrayList()
                            myHelperdataModel.clear()
                            Log.d("response__", response.toString())

                            val mResponseBody: JsonObject = response.body()!!
                            val responseStatus: Boolean =
                                mResponseBody.get("response").getAsBoolean()

                            if (responseStatus) {
                                val data: JsonArray =
                                    mResponseBody.getAsJsonObject().getAsJsonArray("data")
                                if (data.size() > 0) {

                                    for (i in 0 until data.size()) {
                                        val id: String =
                                            data.get(i).asJsonObject.get("id").asString
                                        val role_id: String =
                                            data.get(i).asJsonObject.get("role_id").asString
                                        val rolename: String =
                                            data.get(i).asJsonObject.get("rolename").asString
                                        val id_card_id: String =
                                            data.get(i).asJsonObject.get("id_card_id").asString
                                        val id_card_name: String =
                                            data.get(i).asJsonObject.get("id_card_name").asString
                                        val id_card_no: String =
                                            data.get(i).asJsonObject.get("id_card_no").asString
                                        val mobile_no: String =
                                            data.get(i).asJsonObject.get("mobile_no").asString

                                        val address: String =
                                            data.get(i).asJsonObject.get("address").asString
                                        val fullname: String =
                                            data.get(i).asJsonObject.get("fullname").asString
                                        val dob: String =
                                            data.get(i).asJsonObject.get("dob").asString
                                        val gender: String =
                                            data.get(i).asJsonObject.get("gender").asString
                                        val father_name: String =
                                            data.get(i).asJsonObject.get("father_name").asString
                                        val virification_status: String =
                                            data.get(i).asJsonObject.get("virification_status").asString
                                        val virification_date: String =
                                            data.get(i).asJsonObject.get("virification_date").asString
                                        val vendor_image: String =
                                            data.get(i).asJsonObject.get("vendor_image").asString
                                        val id_card_image: String =
                                            data.get(i).asJsonObject.get("id_card_image").asString
                                        val qr_code: String =
                                            data.get(i).asJsonObject.get("qr_code").asString


                                        var helperModel = MyHelperDataModel(

                                            id,
                                            role_id,
                                            rolename,
                                            id_card_id,
                                            id_card_name,
                                            id_card_no,
                                            mobile_no,
                                            address,
                                            fullname,
                                            dob,
                                            gender,
                                            father_name,
                                            virification_status,
                                            virification_date,
                                            vendor_image,
                                            id_card_image,
                                            qr_code

                                        )
                                        myHelperdataModel.add(helperModel)

                                    }
                                    recyclerView.layoutManager = LinearLayoutManager(
                                        this@HelperDataListForm,
                                        LinearLayout.VERTICAL,
                                        false
                                    )

                                    val adapter = MyHelperDataListAdapter(myHelperdataModel
                                    )
                                    recyclerView.adapter = adapter
                                    adapter.notifyDataSetChanged()

                                    recyclerView.setItemViewCacheSize(myHelperdataModel.size)
                                    progerssProgressDialog.dismiss()


                                }

                            } else {
                                progerssProgressDialog.dismiss()
                            }


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
    }
}
