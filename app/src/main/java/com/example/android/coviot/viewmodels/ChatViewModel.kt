package com.example.android.coviot.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.coviot.database.getDatabase
import com.example.android.coviot.repository.CoviotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)

    private val coviotRepository = CoviotRepository(database)

    init {
        viewModelScope.launch {
            coviotRepository.refreshCoviot()
        }
    }

    val chats = coviotRepository.chats

    fun sendMessage(message: String) {
        viewModelScope.launch {
            coviotRepository.sendMessage(message, "user")
        }
        sortMessageToQuery(message)
    }

    fun welcomeMessage() {
        viewModelScope.launch {
            coviotRepository.sendMessage("Hello, this is Coviot, I can help you with Covid-19 stats, feel free to ask some questions", "bot")
        }
    }

    private fun sortMessageToQuery(msg: String) {
        with(msg.toUpperCase()) {
            when {
                contains("CASE") -> when {
                    contains("TOTAL") -> fetchWithReply("Total Active Cases ", "GLOBAL")
                    else -> fetchWithReply(takeLast(3) + " Active Cases ", takeLast(3).trim())
                }
                contains("DEATH") -> when {
                    contains("TOTAL") -> fetchWithReply("Total Deaths ", "GLOBAL")
                    else -> fetchWithReply(takeLast(3) + " Deaths ", takeLast(3).trim())
                }
                else -> sendSorryMessage()
            }
        }
    }

    private fun fetchWithReply(replyMsg: String, queryParam: String) {
        viewModelScope.launch {
            coviotRepository.replyAs(replyMsg, queryParam)
        }
    }

    private fun sendSorryMessage() {
        viewModelScope.launch {
            coviotRepository.sendMessage("Sorry, I cant help you with that!!!", "bot")
        }
    }
}