package manacle.mcivic.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import manacle.mcivic.R
 import manacle.mcivic.adapter.MyViewPageStateAdapter
import manacle.mcivic.fragments.*

class VisitorsDataActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitors_data)
        initViews()
       // setStatePageAdapter()

       /* tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })*/
    }

    private fun initViews() {
      /*  tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)*/
    }

    private fun setStatePageAdapter() {
        val myViewPageStateAdapter: MyViewPageStateAdapter =
            MyViewPageStateAdapter(supportFragmentManager)
        myViewPageStateAdapter.addFragment(GuestFragment(), "Guest")
        myViewPageStateAdapter.addFragment(CabFragment(), "Cab")
        myViewPageStateAdapter.addFragment(DeliveryFragment(), "Delivery")
        myViewPageStateAdapter.addFragment(OtherFragment(), "Other")
        viewPager.adapter = myViewPageStateAdapter
        tabLayout.setupWithViewPager(viewPager, true)

    }
}

