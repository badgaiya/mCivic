package manacle.mcivic.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_town.*
import manacle.mcivic.R
import manacle.mcivic.adapter.TownAdapter
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.data.model.RegistrationLayer
import manacle.mcivic.data.model.TownLayer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpTown : AppCompatActivity(){

    lateinit var progerssProgressDialog: ProgressDialog
    lateinit var townLayerArrayList :ArrayList<RegistrationLayer>
    lateinit var layerArrayList :ArrayList<TownLayer>
    lateinit var townRecyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_town)
        toolbar.setTitle("Town/City")

        townRecyclerView = findViewById(R.id.townRecyclerView) as RecyclerView

          getTownData()
     }

    private fun getTownData() {
        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Data is loading.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.getTownLayer()

        call.enqueue(object : Callback<JsonObject> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        townLayerArrayList= ArrayList()
                        townLayerArrayList.clear()
                        val mResponseBody: JsonObject = response.body()!!
                        val responseStatus: Boolean = mResponseBody.get("response").getAsBoolean()

                        if (responseStatus) {
                            val data: JsonArray =
                                mResponseBody.getAsJsonObject().getAsJsonArray("data")
                            if (data.size() > 0) {

                                for (i in 0 until data.size()) {
                                    val town_id: String =
                                        data.get(i).asJsonObject.get("town_id").asString
                                    val town_name: String =
                                        data.get(i).asJsonObject.get("town_name").asString
                                    val layer_count: String =
                                        data.get(i).asJsonObject.get("layer_count").asString
                                    val layer: JsonArray =
                                        data.get(i).asJsonObject.getAsJsonArray("layer")
                                    if (layer.size() > 0) {
                                        layerArrayList = ArrayList();
                                        layerArrayList.clear()
                                        for (j in 0 until layer.size()) {
                                            val id: String =
                                                layer.get(j).asJsonObject.get("id").asString
                                            val name: String =
                                                layer.get(j).asJsonObject.get("name").asString

                                            var townLayer = TownLayer(id,name)
                                            layerArrayList.add(townLayer)
                                        }
                                    }
                                    var registrationLayer = RegistrationLayer(town_id,town_name,layer_count,layerArrayList)
                                    townLayerArrayList.add(registrationLayer)
                                }
                            }
                        }
                    }
                    progerssProgressDialog.dismiss()
                    townRecyclerView.layoutManager = LinearLayoutManager(this@SignUpTown, LinearLayout.VERTICAL, false)
                    val adapter = TownAdapter( townLayerArrayList)
                    townRecyclerView.adapter = adapter
                }

            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })
    }
}
