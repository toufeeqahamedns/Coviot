package com.example.android.coviot.domain

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

