package com.example.android.coviot.domain

import com.example.android.coviot.database.ChatEntity

data class CoviotData(
    val countryCode: String,
    val country: String,
    val newConfirmed: String,
    val totalConfirmed: String,
    val newDeaths: String,
    val totalDeaths: String,
    val newRecovered: String,
    val totalRecovered: String,
    val date: String,
)

sealed class DataItem {
    data class Chats(val chat: ChatEntity) : DataItem() {
        override val id = chat.id
        override val sender = chat.sender
        override val message = chat.message
        override val sentTime = chat.sentTime
    }

    abstract val id: Long
    abstract val sender: String
    abstract val message: String
    abstract val sentTime: String
}