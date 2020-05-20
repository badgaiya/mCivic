package manacle.mcivic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.SupportMapFragment.newInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import manacle.mcivic.R
import manacle.mcivic.fragments.CheckedInFragment.Companion.newInstance
import manacle.mcivic.fragments.CheckedOutFragment.Companion.newInstance
import manacle.mcivic.ui.login.VisitorsDataActivity
import java.lang.reflect.Array.newInstance


class PreAlertFragment : Fragment() {
    lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_pre_alert, container, false)
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener({

            val intent = Intent (this@PreAlertFragment.context, VisitorsDataActivity::class.java)
            startActivity(intent)


        })
        return view
    }




    /*companion object {
        fun newInstance(`val`: Int): CounterSaleDynamicFragment {
            val fragment = CounterSaleDynamicFragment()
            val args = Bundle()
            args.putInt("someInt", `val`)
            fragment.setArguments(args)
            return fragment
        }
    }*/
}

