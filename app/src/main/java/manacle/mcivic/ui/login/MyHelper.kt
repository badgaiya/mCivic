package manacle.mcivic.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_my_helper.*
import manacle.mcivic.R
import manacle.mcivic.adapter.DashBoardGridRecyclerAdapter
import manacle.mcivic.adapter.MyHelperDataAdapter
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.data.model.MyHelperModel
import manacle.mcivic.database.CivicRoomDatabase
import manacle.mcivic.database.DashboardModuleDao
import manacle.mcivic.database.DashboardModuleTable
import manacle.mcivic.gridHolder.GridItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyHelper : AppCompatActivity() {
    private var db: CivicRoomDatabase? = null
     lateinit var  recyclerView:RecyclerView
     private var myHelperModel: ArrayList<MyHelperModel> = ArrayList()
    //lateinit var context:Context

    var company_id:String=""

    lateinit var progerssProgressDialog: ProgressDialog


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_helper)


         recyclerView = findViewById(R.id.recyclerViewMyhelper) as RecyclerView


        myHelperDataList()
    }


    private fun myHelperDataList() {
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Data is loading.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        //myHelperModel.clear()
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.myHelperDataList("37")

        call.enqueue(object : Callback<JsonObject> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                try {
                    if (response != null) {
                        if (response.code() == 200) {
                            myHelperModel = ArrayList()
                            myHelperModel.clear()
                            Log.d("response____",response.toString())

                            val mResponseBody: JsonObject = response.body()!!
                            val responseStatus: Boolean =
                                mResponseBody.get("response").getAsBoolean()

                            if (responseStatus) {
                                val data: JsonArray =
                                    mResponseBody.getAsJsonObject().getAsJsonArray("data")
                                if (data.size() > 0) {

                                    for (i in 0 until data.size()) {
                                        val role_id: String =
                                            data.get(i).asJsonObject.get("role_id").asString
                                        val rolename: String =
                                            data.get(i).asJsonObject.get("rolename").asString


                                        var helperModel = MyHelperModel(role_id, rolename)
                                        myHelperModel.add(helperModel)

                                    }
                                    recyclerView.layoutManager = LinearLayoutManager(this@MyHelper, LinearLayout.VERTICAL, false)

                                    val adapter = MyHelperDataAdapter(this@MyHelper,myHelperModel)
                                    recyclerView.adapter = adapter
                                    adapter.notifyDataSetChanged()

                                    recyclerView.setItemViewCacheSize(myHelperModel.size)
                                    progerssProgressDialog.dismiss()


                                }

                            }else{
                                progerssProgressDialog.dismiss()
                            }


                        }
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                progerssProgressDialog.dismiss()

            }

        })
    }


}


