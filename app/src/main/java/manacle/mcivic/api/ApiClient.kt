package manacle.mcivic.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    var BASE_URL:String="http://production.msell.in/public/api/"
var retrofit:Retrofit? = null
    var gson = GsonBuilder()
        .setLenient()
        .create()
    fun getClient():Retrofit {


             if (retrofit!=null)
             retrofit=null

              retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit as Retrofit

        }







}