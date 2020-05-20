package manacle.mcivic.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_sign_up.*
import manacle.mcivic.R
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity :AppCompatActivity(){
    lateinit var progerssProgressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        toolbar.setTitle("Sign Up")
        request_otp_btn.setOnClickListener {

            getOTPData()
        }
    }

    private fun getOTPData() {
        progerssProgressDialog=ProgressDialog(this)
        progerssProgressDialog.setTitle("OTP will sent to your Mobile Number.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        val service  = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.getOTP(mobile_no_et.text.toString(), "2020-03-02", "21:00:00")

        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                progerssProgressDialog.dismiss()
                intent= Intent(this@SignupActivity,SignupOTPActivity::class.java)
                intent.putExtra("mobile_no", mobile_no_et.text.toString())

                startActivity(intent)
            }

            override fun onFailure(call: Call< JsonObject>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })
    }


}



