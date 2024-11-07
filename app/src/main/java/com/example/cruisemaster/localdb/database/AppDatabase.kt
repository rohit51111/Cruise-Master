package com.example.cruisemaster.localdb.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cruisemaster.localdb.dao.CrewInfoDao
import com.example.cruisemaster.localdb.dao.GuestInfoDao
import com.example.cruisemaster.localdb.tables.Crew
import com.example.cruisemaster.localdb.tables.Guest

@Database(entities = [Guest::class, Crew::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun guestInfoDao(): GuestInfoDao
    abstract fun crewInfoDao(): CrewInfoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

   /* companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).build()
    }*/
}
