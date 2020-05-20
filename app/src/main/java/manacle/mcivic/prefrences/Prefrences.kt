package manacle.mcivic.prefrences

import android.content.Context
import android.content.SharedPreferences

object Prefrences {

  val MyPREFERENCES:String="MyPrefs"
      fun setPrefrences(  context:Context,   key:String,   value:String) {
   val prefrence:SharedPreferences=context.getSharedPreferences(MyPREFERENCES,0)
      val  editor: SharedPreferences.Editor =prefrence.edit()
        editor.putString(key, value)
        editor.commit()

    }
      fun getPrefrences(  context:Context,   key:String   ): String? {
        val prefrence:SharedPreferences=context.getSharedPreferences(MyPREFERENCES,0)
          return prefrence.getString(key,"")
    }

      fun clearPrefrences(  context:Context,   key:String   ) {
        val prefrence:SharedPreferences=context.getSharedPreferences(MyPREFERENCES,0)
        prefrence.edit().remove(key).commit()
     }

 }