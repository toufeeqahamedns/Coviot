package com.example.android.coviot.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

private lateinit var INSTANCE: CoviotDatabse

@Dao
interface CoviotDao {
    @Query("select * from coviot where countryCode = :code")
    fun getCoviot(code: String): CoviotEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg coviots: CoviotEntity)
}

@Dao
interface ChatDao {
    @Query("select * from chat")
    fun getMessages(): LiveData<List<ChatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: ChatEntity)
}

@Database(entities = [CoviotEntity::class, ChatEntity::class], version = 1)
abstract class CoviotDatabse : RoomDatabase() {
    abstract val coviotDao: CoviotDao

    abstract val chatDao: ChatDao
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