package com.example.android.coviot.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.coviot.database.getDatabase
import com.example.android.coviot.repository.CoviotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(application: Application): AndroidViewModel(application){

    private val database = getDatabase(application)

    private val coviotRepository = CoviotRepository(database)

    init {
        viewModelScope.launch {
            coviotRepository.refreshCoviot()
        }
    }

    fun query() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val res = database.coviotDao.getCoviot("IN")
                println(res[0].newConfirmed)
            }
        }
    }
}