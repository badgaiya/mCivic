package manacle.mcivic.ui.login

import android.app.ProgressDialog
 import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
 import kotlinx.android.synthetic.main.activity_sign_up_otp.*
 import kotlinx.android.synthetic.main.activity_sign_up_otp.request_otp_btn
import manacle.mcivic.R
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.prefrences.Prefrences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupOTPActivity : AppCompatActivity(){
    lateinit var progerssProgressDialog: ProgressDialog

    var mobile_no=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_otp)
        toolbar.setTitle("Verify OTP")

        val bundle:Bundle = intent.extras!!
          mobile_no = bundle.get("mobile_no").toString()
        mobile_no_tv.text= mobile_no as CharSequence?

        request_otp_btn.setOnClickListener {

            getVerifyOTPData()
        }
     }

    private fun getVerifyOTPData() {
        progerssProgressDialog= ProgressDialog(this)
        progerssProgressDialog.setTitle("OTP will verify.Please wait!!")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        val service  = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.getVerifyOTP(mobile_no,otp_no_et.text.toString(), "2020-03-02"+" "+ "21:00:00")

        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                progerssProgressDialog.dismiss()


                Prefrences.setPrefrences(this@SignupOTPActivity,"Signup_information","Signup_information")
                Prefrences.setPrefrences(this@SignupOTPActivity,"user_mobile_no", mobile_no)

                intent= Intent(this@SignupOTPActivity,SignUpTown::class.java)

                startActivity(intent)
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })
    }

}