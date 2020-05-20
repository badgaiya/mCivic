package manacle.mcivic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import manacle.mcivic.R

class CheckedInFragment : Fragment() {

    lateinit var root1View: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root1View =  inflater.inflate(R.layout.fragment_chech_in, container, false)
         return root1View
    }


    companion object {

         @JvmStatic
        fun newInstance(param1: String, param2: String) =
             CheckedInFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}