package com.example.android.coviot.database

import android.content.Context
import androidx.room.*

private lateinit var INSTANCE: CoviotDatabse

@Dao
interface CoviotDao {
    @Query("select * from coviot where countryCode = :code")
    fun getCoviot(code: String): List<CoviotEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg coviots: CoviotEntity)
}

@Database(entities = [CoviotEntity::class], version = 1)
abstract class CoviotDatabse : RoomDatabase() {
    abstract val coviotDao: CoviotDao
}

fun getDatabase(context: Context): CoviotDatabse {
    synchronized(CoviotDatabse::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(
                    context.applicationContext,
                    CoviotDatabse::class.java,
                    "coviot"
                ).build()
        }
    }
    return INSTANCE
}