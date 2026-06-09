package com.example.ungdungthoitiet.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "thanh_pho_yeu_thich")
data class ThanhPhoEntity(
    @PrimaryKey val tenThanhPho: String
)

@Dao
interface ThanhPhoDao {
    @Query("SELECT * FROM thanh_pho_yeu_thich")
    suspend fun layTatCa(): List<ThanhPhoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun themThanhPho(thanhPho: ThanhPhoEntity)

    @Query("DELETE FROM thanh_pho_yeu_thich WHERE tenThanhPho = :ten")
    suspend fun xoaThanhPho(ten: String)
}

@Database(entities = [ThanhPhoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun thanhPhoDao(): ThanhPhoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun layDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "thoitiet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
