package manacle.mcivic.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_main.*
import manacle.mcivic.R
import manacle.mcivic.adapter.DashBoardGridRecyclerAdapter
import manacle.mcivic.database.CivicRoomDatabase
import manacle.mcivic.database.DashboardModuleDao
import manacle.mcivic.database.DashboardModuleTable
import manacle.mcivic.gridHolder.GridItemDecoration

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var db: CivicRoomDatabase? = null
    private var dashboardDao: DashboardModuleDao? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    var toggle: ActionBarDrawerToggle? = null
    var list = listOf<DashboardModuleTable>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = CivicRoomDatabase.getAppDataBase(context = this)
        val fab: ExtendedFloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(this)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.setDrawerListener(toggle)
        toggle!!.syncState()


        /* appBarConfiguration = AppBarConfiguration(setOf(
             R.id.nav_home, R.id.my_helper, R.id.nav_slideshow), drawerLayout)
 */
        preparemoduledata()


    }


    fun preparemoduledata() {

        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                db
                dashboardDao = db?.dashboardModuleDeo()
                dashboardDao?.deleteAllDashboardModule()
                var module1 =
                    DashboardModuleTable(module_id = "1", module_name = "About that society")
                var module2 = DashboardModuleTable(module_id = "2", module_name = "Directory")
                var module3 =
                    DashboardModuleTable(module_id = "3", module_name = "Helpline Numbers")
                var module4 = DashboardModuleTable(module_id = "4", module_name = "Nearby Places")
                var module5 = DashboardModuleTable(module_id = "5", module_name = "Notifications")
                var module6 = DashboardModuleTable(
                    module_id = "6",
                    module_name = "Society wise Banners changes"
                )
                var module7 = DashboardModuleTable(module_id = "7", module_name = "News & Events")
                var module8 = DashboardModuleTable(module_id = "8", module_name = "Feedback")
                var module9 = DashboardModuleTable(module_id = "9", module_name = "ScanGarbage")
                var module10 = DashboardModuleTable(module_id = "10", module_name = "Complaint")
                var module11 =
                    DashboardModuleTable(module_id = "11", module_name = "FieldInspection")

                with(dashboardDao) {
                    this?.insertDashboard(module1)
                    this?.insertDashboard(module2)
                    this?.insertDashboard(module3)
                    this?.insertDashboard(module4)
                    this?.insertDashboard(module5)
                    this?.insertDashboard(module6)
                    this?.insertDashboard(module7)
                    this?.insertDashboard(module8)
                    this?.insertDashboard(module9)
                    this?.insertDashboard(module10)
                    this?.insertDashboard(module11)


                }
                list = db?.dashboardModuleDeo()?.getAlphabetizedDashboardModule()!!
                recyclerViewDashboard.layoutManager = GridLayoutManager(this, 2)

                //This will for default android divider
                recyclerViewDashboard.addItemDecoration(GridItemDecoration(10, 2))

                val movieListAdapter = DashBoardGridRecyclerAdapter()
                recyclerViewDashboard.adapter = movieListAdapter
                movieListAdapter.setMovieList(list)
                recyclerViewDashboard.setItemViewCacheSize(list.size)

            }

        //fetch Records


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_helper -> {

                val intent = Intent(this, MyHelper::class.java)
                startActivity(intent)
            }
            R.id.nav_visitors -> {

                val intent = Intent(this, Visitors::class.java)
                startActivity(intent)
            }


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}