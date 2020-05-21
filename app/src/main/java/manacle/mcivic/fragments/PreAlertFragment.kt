package manacle.mcivic.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment.newInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_visitors_data.*
import manacle.mcivic.R
import manacle.mcivic.adapter.MyHelperDataListAdapter
import manacle.mcivic.adapter.VisitorrDataListAdapter
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.data.model.MyHelperDataModel
import manacle.mcivic.data.model.VisitorDataModel
import manacle.mcivic.fragments.CheckedInFragment.Companion.newInstance
import manacle.mcivic.fragments.CheckedOutFragment.Companion.newInstance
import manacle.mcivic.ui.login.VisitorsDataActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Array.newInstance


class PreAlertFragment : Fragment() {
    lateinit var fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    private var visitorDataModel: ArrayList<VisitorDataModel> = ArrayList()
    lateinit var progerssProgressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_pre_alert, container, false)
        fab = view.findViewById(R.id.fab)
        recyclerView = view.findViewById(R.id.recyclerViewMyhelper) as RecyclerView
        getDataList()
        fab.setOnClickListener({

            val intent = Intent(this@PreAlertFragment.context, VisitorsDataActivity::class.java)
            startActivity(intent)


        })
        return view
    }


    private fun getDataList() {
        progerssProgressDialog = ProgressDialog(activity)
        progerssProgressDialog.setTitle("Data is loading.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        //myHelperModel.clear()
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.visitorDataList("37", "0")

        call.enqueue(object : Callback<JsonObject> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                try {
                    if (response != null) {
                        if (response.code() == 200) {
                            visitorDataModel = ArrayList()
                            visitorDataModel.clear()
                            Log.d("response__", response.toString())

                            val mResponseBody: JsonObject = response.body()!!
                            val responseStatus: Boolean =
                                mResponseBody.get("response").getAsBoolean()

                            if (responseStatus) {
                                val data: JsonArray =
                                    mResponseBody.getAsJsonArray("data")
                                if (data.size() > 0) {

                                    for (i in 0 until data.size()) {
                                        val id: String =
                                            data.get(i).asJsonObject.get("id").asString

                                        val company_id: String =
                                            data.get(i).asJsonObject.get("company_id").asString

                                        val visitor_mobile: String =
                                            data.get(i).asJsonObject.get("visitor_mobile").asString
                                        val visitor_name: String =
                                            data.get(i).asJsonObject.get("visitor_name").asString
                                        val visitor_schedule_time: String =
                                            data.get(i).asJsonObject.get("visitor_schedule_time").asString
                                        val visitor_stay_period: String =
                                            data.get(i).asJsonObject.get("visitor_stay_period").asString
                                        val visitor_vehicle_type: String =
                                            data.get(i).asJsonObject.get("visitor_vehicle_type").asString
                                        val visitor_vehicle_number: String =
                                            data.get(i).asJsonObject.get("visitor_vehicle_number").asString

                                        val status: String =
                                            data.get(i).asJsonObject.get("status").asString
                                        val created_at: String =
                                            data.get(i).asJsonObject.get("created_at").asString
                                        val created_by: String =
                                            data.get(i).asJsonObject.get("created_by").asString
                                      /*  val updated_at: String =
                                            data.get(i).asJsonObject.get("updated_at").asString
                                        val updated_by: String =
                                            data.get(i).asJsonObject.get("updated_by").asString
*/

                                        var visitorModel = VisitorDataModel(
                                            id,
                                            company_id,
                                            visitor_mobile,
                                            visitor_name,
                                            visitor_schedule_time,
                                            visitor_stay_period,
                                            visitor_vehicle_type,
                                            visitor_vehicle_number,
                                            status,
                                            created_at,
                                            created_by
                                           /* "",
                                            ""
*/
                                        )
                                        visitorDataModel.add(visitorModel)

                                    }
                                    recyclerView.layoutManager = LinearLayoutManager(
                                        activity,
                                        LinearLayout.VERTICAL,
                                        false
                                    )

                                    val adapter = VisitorrDataListAdapter(
                                        visitorDataModel
                                    )
                                    recyclerView.adapter = adapter
                                    adapter.notifyDataSetChanged()

                                    recyclerView.setItemViewCacheSize(visitorDataModel.size)
                                    progerssProgressDialog.dismiss()


                                }

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

    /*companion object {
        fun newInstance(`val`: Int): CounterSaleDynamicFragment {
            val fragment = CounterSaleDynamicFragment()
            val args = Bundle()
            args.putInt("someInt", `val`)
            fragment.setArguments(args)
            return fragment
        }
    }*/
}

