package manacle.mcivic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import manacle.mcivic.R
import manacle.mcivic.uihelper.Uihelper

class StartFirstFragment     : Fragment() {
    private val uiHelper = Uihelper()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_start_first, container, false)
//        view.findViewById< TextV>(R.id.notificationAlertsTextView).typeface =
//            uiHelper.getTypeFace(TypeFaceEnum.HEADING_TYPEFACE, activity!!)
//        view.findViewById<TextView>(R.id.notificationAlertsSubTitleTextView).typeface =
//            uiHelper.getTypeFace(
//                TypeFaceEnum.SEMI_TITLE_TYPEFACE, activity!!
//            )
        return view
    }
}