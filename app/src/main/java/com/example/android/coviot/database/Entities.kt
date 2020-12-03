package com.example.android.coviot.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.coviot.domain.CoviotData


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