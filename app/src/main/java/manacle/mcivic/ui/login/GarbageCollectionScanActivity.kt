package manacle.mcivic.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_garbage_collection_scan.toolbar
import manacle.mcivic.R
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.common.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GarbageCollectionScanActivity:AppCompatActivity(){
    internal var bitmap: Bitmap? = null
    private var data_et: TextView? = null
    private var scanimageView: ImageView? = null
    private var fab_submit: Button? = null
    lateinit var progerssProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garbage_collection_scan)

        toolbar.setTitle("Scan Garbage Point")
        scanimageView = findViewById(R.id.scanimageView) as ImageView
        data_et = findViewById(R.id.data_et) as TextView
        fab_submit = findViewById(R.id.fab_submit) as Button

        scanimageView!!.setOnClickListener {
            run {
                IntentIntegrator(this).setOrientationLocked(false);

                IntentIntegrator(this).initiateScan();
            }
        }

        fab_submit!!.setOnClickListener {
            submitdata()
        }
    }

          fun submitdata(){


            progerssProgressDialog= ProgressDialog(this)
            progerssProgressDialog.setTitle("Please wait for proceeding!!")
            progerssProgressDialog.setCancelable(false)
            progerssProgressDialog.show()
            val service  = ApiClient.getClient().create(ApiInterface::class.java)
            val call = service.sendGarbageQRScan(
                "name",
                "user_mobile_no",
                Constants.getDate(),
                Constants.getTime())

            call.enqueue(object : Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    progerssProgressDialog.dismiss()

                    intent= Intent(this@GarbageCollectionScanActivity,DashboardActivity::class.java)

                    startActivity(intent)
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    progerssProgressDialog.dismiss()
                }

            })
        }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                 data_et!!.text = result.contents
            } else {
                data_et!!.text = "scan failed"
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    companion object {

        var data_et: TextView? = null
    }

    }

