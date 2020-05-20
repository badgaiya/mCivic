package manacle.mcivic.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DashboardModuleDao {

    @Query("SELECT * from dashboard_module_table ORDER BY module_id ASC")
    fun getAlphabetizedDashboardModule(): List<DashboardModuleTable>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashboard(dashboardModuleTable: DashboardModuleTable)



    @Query("DELETE FROM dashboard_module_table")
      fun deleteAllDashboardModule()
}