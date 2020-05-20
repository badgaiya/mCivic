package manacle.mcivic.common

import android.text.format.Time
import java.text.SimpleDateFormat
import java.util.*

object Constants {

   fun getDate():String{
       val c:Calendar= Calendar.getInstance()
       val df:SimpleDateFormat= SimpleDateFormat("yyyy-MM-dd")
       val date=df.format(c.time)
       return date
   }

    fun getTime():String{
        val t: Time? =Time(Time.getCurrentTimezone())
        t!!.setToNow()
        val time=t.format("%k:%M:%S")
        return time
    }

    fun orderId():String{
        val c:Calendar= Calendar.getInstance()
        val df:SimpleDateFormat= SimpleDateFormat("yyyyMMddhhmmss")
        val date=df.format(c.time)
        return date
    }


}