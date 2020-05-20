package manacle.mcivic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import manacle.mcivic.R
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class CheckedOutFragment : Fragment() {
     private var param1: String? = null
    private var param2: String? = null
    lateinit var root1View: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root1View =  inflater.inflate(R.layout.fragment_checked_out, container, false)
         return root1View;
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckedOutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}