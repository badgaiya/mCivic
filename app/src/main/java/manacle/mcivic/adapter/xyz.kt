//import android.widget.Toast
//
//
//fun validate(): Boolean {
//    if (mobie_no.length() === 0) {
//        Toast.makeText(applicationContext, "mobile number is mandatory", Toast.LENGTH_LONG)
//            .show()
//        return false
//    } else if (mobie_no.length() > 0 && mobie_no.length() !== 10
//    ) {
//        Toast.makeText(applicationContext, "Enter correct mobile number", Toast.LENGTH_LONG).show()
//        return false
//    } else if (mobie_no.length() === 10) {
//        UserApi.mobileExist(mobie_no)
//        while (existmsg == null) {
//
//        }
//        return if (existmsg != null && existmsg.equals("exist")) { Toast.makeText(applicationContext,
//            "mobile number already exist please login", Toast.LENGTH_LONG).show()
//            false
//        } else if (existmsg != null && existmsg.equals("notExist")) {
//            true
//        } else {
//            Toast.makeText(applicationContext, "Some error occured plz try again", Toast.LENGTH_LONG).show()
//            false
//        }
//    }
//    return true
//}
//
