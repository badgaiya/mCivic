package manacle.mcivic.api

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("registration_civic")
    fun getOTP(

        @Field("mobile_no") mobile_no: String,
        @Field("date") date: String,
        @Field("time") time: String
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("otp_verification")
    fun getVerifyOTP(

        @Field("mobile_no") mobile_no: String,
        @Field("otp") date: String,
        @Field("date_time") time: String
    ): Call<JsonObject>

    @POST("registration_layer")
    fun getTownLayer(): Call<JsonObject>

    @FormUrlEncoded
    @POST("getFurtherLayer")
    fun getFurtherLayer(
        @Field("id") mobile_no: String,
        @Field("layer") date: String

    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("vendor_catgory")
    fun myHelperDataList(
        @Field("company_id") company_id: String

    ): Call<JsonObject>
    @FormUrlEncoded
    @POST("visitor_master_dropdown")
    fun spinnerDataList(
        @Field("company_id") company_id: String

    ): Call<JsonObject>
    @FormUrlEncoded
    @POST("vendor_registration_list")
    fun myHelpergetDataList(
        @Field("company_id") company_id: String,
        @Field("login_id") login_id: String

    ): Call<JsonObject>


    @FormUrlEncoded
    @POST("vistior_details")
    fun visitorDataList(
        @Field("company_id") company_id: String,
        @Field("login_id") login_id: String

    ): Call<JsonObject>

 @FormUrlEncoded
    @POST("vendor_details")
    fun getMobileExist(
        @Field("company_id") company_id: String,
        @Field("mobile_no") mobile_no: String

    ): Call<JsonObject>


    @FormUrlEncoded
    @POST("register_civillian")
    fun sendRegistrationData(

        @Field("name") name: String,
        @Field("username") mobile_no: String,
        @Field("email") email: String,
        @Field("password") passowrd: String,
        @Field("location_id") location_id: String,
        @Field("company_id") company_id: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("mobile") mobile: String,
        @Field("layer_id") layer_id: String

    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("civillian_login")
    fun getLogin(
        @Field("v_name") v_name: String,
        @Field("v_code") v_code: String,
        @Field("uname") uname: String,
        @Field("pass") pass: String

    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("garbage_qrcode_scan")
    fun sendGarbageQRScan(
        @Field("company_id") company_id: String,
        @Field("location_id") location_id: String,
        @Field("date") date: String,
        @Field("time") time: String

    ): Call<JsonObject>


    @FormUrlEncoded
    @Multipart
    @POST("vendor_registration")
    fun sendVendorData(
        @Field("company_id") company_id: String,
        @Field("reg_no") reg_no: String,
        @Field("name") name: String,
        @Field("mobile_no") mobile_no: String,
        @Field("address") address: String,
        @Field("role_id") role_id: String,
        @Field("id_card_id") id_card_no: String,
        @Field("gender_id") gender_id: String,
        @Field("dob") dob: String,
        @Field("father_name") father_name: String,
        @Field("login_id") login_id: String,
        @Field("id_card_no") person_id: String,
        @Part image: MultipartBody.Part,
        @Part image1: MultipartBody.Part
    ):Call<JsonObject>




    @FormUrlEncoded

    @POST("visitors_submit")
    fun sendVisitorDataSubmit(
        @Field("company_id") company_id: String,
        @Field("login_id") login_id: String,
        @Field("visitor_Mobile") reg_no: String,
        @Field("Visitor_Name") name: String,
        @Field("Visitor_Scedule_time") mobile_no: String,
        @Field("Recurring_dropdown") address: String,
        @Field("Vehicle_Type_dropdown") role_id: String,
        @Field("Vehicle_Number") id_card_no: String

    ):Call<JsonObject>









}