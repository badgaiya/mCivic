package manacle.mcivic.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dashboard_module_table")
class DashboardModuleTable(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name="module_id") val module_id: String,
    @ColumnInfo(name = "module_name") val module_name: String
)