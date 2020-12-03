package com.example.android.coviot.repository

import com.example.android.coviot.database.CoviotDatabse
import com.example.android.coviot.network.Network
import com.example.android.coviot.network.countryAsDatabaseModel
import com.example.android.coviot.network.globalAsDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoviotRepository(private val database: CoviotDatabse) {

    suspend fun refreshCoviot() {
        withContext(Dispatchers.IO) {
            val coviotData = Network.coviots.getCoviotsAsync().await()
            database.coviotDao.insertAll(*coviotData.countryAsDatabaseModel())
            database.coviotDao.insertAll(*coviotData.globalAsDatabaseModel())
        }
    }
}