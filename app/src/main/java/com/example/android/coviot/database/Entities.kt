package com.example.android.coviot.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.coviot.domain.CoviotData
import com.example.android.coviot.domain.DataItem


@Entity(tableName = "coviot")
data class CoviotEntity constructor(
    @PrimaryKey
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

@Entity(tableName = "chat")
data class ChatEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val sender: String = "bot",
    val message: String,
    val sentTime: String = System.currentTimeMillis().toString()
)

fun List<CoviotEntity>.asDomainModel(): List<CoviotData> {
    return map {
        CoviotData(
            countryCode = it.countryCode,
            country = it.country,
            newConfirmed = it.newConfirmed,
            totalConfirmed = it.totalConfirmed,
            newDeaths = it.newDeaths,
            totalDeaths = it.totalDeaths,
            newRecovered = it.newRecovered,
            totalRecovered = it.totalRecovered,
            date = it.date,
        )
    }
}

fun List<ChatEntity>.chatAsDomainModel(): List<DataItem.Chats> {
    return map {
        DataItem.Chats(
            it
        )
    }
}