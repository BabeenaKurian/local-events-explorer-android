package com.babeena.localeventsexplorer.domain.model

data class Event(
    val id: String,
    val title: String,
    val location: String,
    val dateTime: String,
    val imgUrl: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)
