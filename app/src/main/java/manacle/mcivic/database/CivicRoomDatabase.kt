package manacle.mcivic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DashboardModuleTable::class), version = 1, exportSchema = false)

public  abstract class CivicRoomDatabase : RoomDatabase(){

    abstract fun dashboardModuleDeo(): DashboardModuleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CivicRoomDatabase? = null


            fun getAppDataBase(context: Context): CivicRoomDatabase? {
                if (INSTANCE == null){
                    synchronized(CivicRoomDatabase::class){
                        INSTANCE = Room.databaseBuilder(context.applicationContext, CivicRoomDatabase::class.java, "CivicDatabase.db").build()
                    }
                }
                return INSTANCE
            }

            fun destroyDataBase(){
                INSTANCE = null
            }
        }
    }
