package manacle.mcivic.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import manacle.mcivic.R
import manacle.mcivic.adapter.MyViewPageStateAdapter
import manacle.mcivic.fragments.CheckedInFragment
import manacle.mcivic.fragments.CheckedOutFragment
import manacle.mcivic.fragments.PreAlertFragment

class Visitors : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitors)
        initViews()
        setStatePageAdapter()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // setAdapter();


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                //   viewPager.notifyAll();
            }
        })
    }
    private fun  initViews(){
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
    }
    private fun setStatePageAdapter(){
        val myViewPageStateAdapter: MyViewPageStateAdapter = MyViewPageStateAdapter(supportFragmentManager)
        myViewPageStateAdapter.addFragment(CheckedInFragment(),"CHECKED IN")
        myViewPageStateAdapter.addFragment(CheckedOutFragment(),"CHECKED OUT")
        myViewPageStateAdapter.addFragment(PreAlertFragment(),"PRE-ALERT")
        viewPager.adapter=myViewPageStateAdapter
        tabLayout.setupWithViewPager(viewPager,true)

    }
}

