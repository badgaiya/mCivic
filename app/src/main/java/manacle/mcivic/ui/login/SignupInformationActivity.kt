package manacle.mcivic.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
 import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_sign_up_information.*
 import manacle.mcivic.R
import manacle.mcivic.adapter.RegistrationLocationLayerAdapter
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.common.Constants
import manacle.mcivic.data.model.*
import manacle.mcivic.prefrences.Prefrences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupInformationActivity : AppCompatActivity(){

    lateinit var progerssProgressDialog: ProgressDialog
     lateinit var layerArrayList1 :ArrayList<Layer1>
    lateinit var layerArrayList2 :ArrayList<Layer1>
    lateinit var layerArrayList3 :ArrayList<Layer1>
    lateinit var layerArrayList4 :ArrayList<Layer1>
    lateinit var layerArrayList5 :ArrayList<Layer1>
    lateinit var layerArrayList6 :ArrayList<Layer1>
    lateinit var layerArrayList7 :ArrayList<Layer1>
    lateinit var user_mobile_no:String
    lateinit var town_id :String
    lateinit var town_name :String
    lateinit var townLayerArrayList: ArrayList<TownLayer>
    lateinit var townLayerArrayList1: ArrayList<TownLayer1>

    lateinit var  townLayerRecyclerView:RecyclerView
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_information)

        toolbar.setTitle("Residence Information")

        layerArrayList1= ArrayList()
        layerArrayList1.clear()
        layerArrayList2= ArrayList()
        layerArrayList2.clear()
        layerArrayList3= ArrayList()
        layerArrayList3.clear()
        layerArrayList4= ArrayList()
        layerArrayList4.clear()
        layerArrayList5= ArrayList()
        layerArrayList5.clear()
        layerArrayList6= ArrayList()
        layerArrayList6.clear()
        layerArrayList7= ArrayList()
        layerArrayList7.clear()
        townLayerArrayList1= ArrayList()
        townLayerArrayList1.clear()
         val bundle:Bundle = intent.extras!!
        town_id = bundle.get("sign_up_information_town_id").toString()
        town_name = bundle.get("sign_up_information_town_name").toString()
        townLayerArrayList = bundle.get("sign_up_information_layer") as ArrayList<TownLayer>
        townLayerRecyclerView = findViewById(R.id.townLayerRecyclerView) as RecyclerView

        user_mobile_no= Prefrences.getPrefrences(this,"user_mobile_no")!!
        getFurtherLayerData(town_id)


        val fab: ExtendedFloatingActionButton = findViewById(R.id.fab_submit)
        fab.setOnClickListener { view ->
           submitdata()
        }
//        submit_btn.setOnClickListener {
//
//            intent= Intent(this,DashboardActivity::class.java)
//
//            startActivity(intent)
//        }
    }

    private fun submitdata(){
        val jsonArray:JsonArray= JsonArray()
        var location_id:String=""
        var company_id:String=""
        var layer_id:String=""

        val name:String=name_et.text.toString()
        val email:String=email_et.text.toString()
          jsonArray.add(town_id)
        for (townlayer in townLayerArrayList1){
              location_id=townlayer.location_id
            company_id=townlayer.company_id
            layer_id=townlayer.id
             val jsonObject:JsonObject= JsonObject()
            jsonObject.getAsJsonPrimitive(location_id)
            jsonArray.add(location_id)
        }
        val password:String=password_et.text.toString()

        progerssProgressDialog= ProgressDialog(this)
        progerssProgressDialog.setTitle("Please wait for proceeding!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        val service  = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.sendRegistrationData(name,user_mobile_no,email, password,location_id ,company_id,Constants.getDate(),
            Constants.getTime(),user_mobile_no,layer_id)

        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                progerssProgressDialog.dismiss()
                Prefrences.setPrefrences(this@SignupInformationActivity,"Dashboard","Dashboard")

                intent= Intent(this@SignupInformationActivity,DashboardActivity::class.java)

                startActivity(intent)
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })
    }

    private fun getFurtherLayerData(  town_id: String) {
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Data is loading.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.getFurtherLayer(town_id,"1")

        call.enqueue(object : Callback<JsonObject> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                if (response != null) {
                    if (response.code() == 200) {

                    val mResponseBody: JsonObject = response.body()!!
                    val responseStatus: Boolean = mResponseBody.get("response").getAsBoolean()

                    if (responseStatus) {
                        val layer_2: JsonArray =
                            mResponseBody.getAsJsonObject().getAsJsonArray("layer_2")
                        if (layer_2.size() > 0) {

                            for (i in 0 until layer_2.size()) {
                                val id: String =
                                    layer_2.get(i).asJsonObject.get("id").asString
                                val name: String =
                                    layer_2.get(i).asJsonObject.get("name").asString
                                val location_1_id: String =
                                    layer_2.get(i).asJsonObject.get("location_1_id").asString
                                val company_id: String =
                                    layer_2.get(i).asJsonObject.get("company_id").asString
                                 var layer1 = Layer1(id,name,location_1_id,company_id)
                                layerArrayList2.add(layer1)
                            }
                            var id:String=townLayerArrayList.get(0).id
                            var name:String=townLayerArrayList.get(0).name

                            var townLayer = TownLayer1(id,name,"","",layerArrayList2)
                            townLayerArrayList1.add(townLayer)
                         }
                        val layer_3: JsonArray =
                            mResponseBody.getAsJsonObject().getAsJsonArray("layer_3")
                        if (layer_3.size() > 0) {

                            for (i in 0 until layer_3.size()) {
                                val id: String =
                                    layer_3.get(i).asJsonObject.get("id").asString
                                val name: String =
                                    layer_3.get(i).asJsonObject.get("name").asString
                                val location_2_id: String =
                                    layer_3.get(i).asJsonObject.get("location_2_id").asString
                                val company_id: String =
                                    layer_3.get(i).asJsonObject.get("company_id").asString
                                var layer1 = Layer1(id,name,location_2_id,company_id)
                                layerArrayList3.add(layer1)
                            }
                            var id:String=townLayerArrayList.get(1).id
                            var name:String=townLayerArrayList.get(1).name

                            var townLayer = TownLayer1(id,name,"","",layerArrayList3)
                            townLayerArrayList1.add(townLayer)
                        }
                        val layer_4: JsonArray =
                            mResponseBody.getAsJsonObject().getAsJsonArray("layer_4")
                        if (layer_4.size() > 0) {

                            for (i in 0 until layer_4.size()) {
                                val id: String =
                                    layer_4.get(i).asJsonObject.get("id").asString
                                val name: String =
                                    layer_4.get(i).asJsonObject.get("name").asString
                                val location_3_id: String =
                                    layer_4.get(i).asJsonObject.get("location_3_id").asString
                                val company_id: String =
                                    layer_4.get(i).asJsonObject.get("company_id").asString
                                var layer1 = Layer1(id,name,location_3_id,company_id)
                                layerArrayList4.add(layer1)
                            }
                            var id:String=townLayerArrayList.get(2).id
                            var name:String=townLayerArrayList.get(2).name

                            var townLayer = TownLayer1(id,name,"","",layerArrayList4)
                            townLayerArrayList1.add(townLayer)
                        }
                        val layer_5: JsonArray =
                            mResponseBody.getAsJsonObject().getAsJsonArray("layer_5")
                        if (layer_5.size() > 0) {

                            for (i in 0 until layer_5.size()) {
                                val id: String =
                                    layer_5.get(i).asJsonObject.get("id").asString
                                val name: String =
                                    layer_5.get(i).asJsonObject.get("name").asString
                                val location_4_id: String =
                                    layer_5.get(i).asJsonObject.get("location_4_id").asString
                                val company_id: String =
                                    layer_5.get(i).asJsonObject.get("company_id").asString
                                var layer1 = Layer1(id,name,location_4_id,company_id)
                                layerArrayList5.add(layer1)
                            }
                            var id:String=townLayerArrayList.get(3).id
                            var name:String=townLayerArrayList.get(3).name

                            var townLayer = TownLayer1(id,name,"","",layerArrayList5)
                            townLayerArrayList1.add(townLayer)
                        }
                        val layer_6: JsonArray =
                            mResponseBody.getAsJsonObject().getAsJsonArray("layer_6")
                        if (layer_6.size() > 0) {

                            for (i in 0 until layer_6.size()) {
                                val id: String =
                                    layer_6.get(i).asJsonObject.get("id").asString
                                val name: String =
                                    layer_6.get(i).asJsonObject.get("name").asString
                                val location_5_id: String =
                                    layer_6.get(i).asJsonObject.get("location_5_id").asString
                                val company_id: String =
                                    layer_6.get(i).asJsonObject.get("company_id").asString
                                var layer1 = Layer1(id,name,location_5_id,company_id)
                                layerArrayList6.add(layer1)
                            }
                            var id:String=townLayerArrayList.get(4).id
                            var name:String=townLayerArrayList.get(4).name

                            var townLayer = TownLayer1(id,name,"","",layerArrayList6)
                            townLayerArrayList1.add(townLayer)
                        }
                        val layer_7: JsonArray =
                            mResponseBody.getAsJsonObject().getAsJsonArray("layer_7")
                        if (layer_7.size() > 0) {

                            for (i in 0 until layer_7.size()) {
                                val id: String =
                                    layer_7.get(i).asJsonObject.get("id").asString
                                val name: String =
                                    layer_7.get(i).asJsonObject.get("name").asString
                                val location_6_id: String =
                                    layer_7.get(i).asJsonObject.get("location_6_id").asString
                                val company_id: String =
                                    layer_7.get(i).asJsonObject.get("company_id").asString
                                var layer1 = Layer1(id,name,location_6_id,company_id)
                                layerArrayList7.add(layer1)
                            }
                            var id:String=townLayerArrayList.get(5).id
                            var name:String=townLayerArrayList.get(5).name

                            var townLayer = TownLayer1(id,name,"","",layerArrayList7)
                            townLayerArrayList1.add(townLayer)
                        }

                    }
                }


                progerssProgressDialog.dismiss()
                    townLayerRecyclerView.layoutManager = LinearLayoutManager(this@SignupInformationActivity, LinearLayout.VERTICAL, false)
                    val adapter = RegistrationLocationLayerAdapter(town_id, townLayerArrayList1 )
                    townLayerRecyclerView.adapter = adapter
                    }


            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })
    }

}