package com.example.android.coviot.network

import com.example.android.coviot.database.CoviotEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCoviotContainer(val Message: String, val Countries: List<NetworkCoviot>?, val Global: NetworkCoviot?)

@JsonClass(generateAdapter = true)
data class NetworkCoviot(
    val CountryCode: String? = "GLOBAL",
    val Country: String? = "Global",
    val NewConfirmed: String,
    val TotalConfirmed: String,
    val NewDeaths: String,
    val TotalDeaths: String,
    val NewRecovered: String,
    val TotalRecovered: String,
    val Date: String? = System.currentTimeMillis().toString(),
)

fun NetworkCoviotContainer.countryAsDatabaseModel(): Array<CoviotEntity> {
    return Countries?.map {
        CoviotEntity(
            countryCode = it.CountryCode!!,
            country = it.Country!!,
            newConfirmed = it.NewConfirmed,
            totalConfirmed = it.TotalConfirmed,
            newDeaths = it.NewDeaths,
            totalDeaths = it.TotalDeaths,
            newRecovered = it.NewRecovered,
            totalRecovered = it.TotalRecovered,
            date = it.Date!!,
        )
    }!!.toTypedArray()
}

fun NetworkCoviotContainer.globalAsDatabaseModel(): Array<CoviotEntity> {
    return arrayOf(
        CoviotEntity(
            countryCode = Global?.CountryCode!!,
            country = Global.Country!!,
            newConfirmed = Global.NewConfirmed,
            totalConfirmed = Global.TotalConfirmed,
            newDeaths = Global.NewDeaths,
            totalDeaths = Global.TotalDeaths,
            newRecovered = Global.NewRecovered,
            totalRecovered = Global.TotalRecovered,
            date = Global.Date!!,
        )
    )
}