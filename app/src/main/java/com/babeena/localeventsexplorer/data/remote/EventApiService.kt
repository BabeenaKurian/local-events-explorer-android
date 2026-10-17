package com.babeena.localeventsexplorer.data.remote

import com.babeena.localeventsexplorer.domain.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiService {

    @GET("/events")
    suspend fun getEvents(): List<Event>

    @GET("/events/{id}")
    suspend fun getEventById(@Path("id") id:String): Event
}