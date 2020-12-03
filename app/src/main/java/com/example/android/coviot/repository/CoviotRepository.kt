package com.example.android.coviot.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.coviot.database.ChatEntity
import com.example.android.coviot.database.CoviotDatabse
import com.example.android.coviot.database.CoviotEntity
import com.example.android.coviot.database.chatAsDomainModel
import com.example.android.coviot.domain.DataItem
import com.example.android.coviot.network.Network
import com.example.android.coviot.network.countryAsDatabaseModel
import com.example.android.coviot.network.globalAsDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoviotRepository(private val database: CoviotDatabse) {

    val chats: LiveData<List<DataItem.Chats>> =
        Transformations.map(database.chatDao.getMessages()) {
            it.chatAsDomainModel()
        }

    suspend fun refreshCoviot() {
        withContext(Dispatchers.IO) {
            val coviotData = Network.coviots.getCoviotsAsync().await()
            database.coviotDao.insertAll(*coviotData.countryAsDatabaseModel())
            database.coviotDao.insertAll(*coviotData.globalAsDatabaseModel())
        }
    }

    suspend fun sendMessage(message: String, sender: String) {
        withContext(Dispatchers.IO) {
            database.chatDao.insertMessage(ChatEntity(message = message, sender = sender))
        }
    }

    suspend fun replyAs(message: String, queryParam: String) {
        withContext(Dispatchers.IO) {
            val response: String? = when (message.contains("death", ignoreCase = true)) {
                true -> database.coviotDao.getCoviot(queryParam)?.totalDeaths
                false -> database.coviotDao.getCoviot(queryParam)?.totalConfirmed
            }

            val res = when (response) {
                null -> "I couldn't find what you are looking for"
                else -> message + response
            }

            database.chatDao.insertMessage(ChatEntity(message = res, sender = "bot"))
        }
    }
}