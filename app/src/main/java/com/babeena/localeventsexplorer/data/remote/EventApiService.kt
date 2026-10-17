package com.babeena.localeventsexplorer.data.remote

import com.babeena.localeventsexplorer.domain.model.Event
import retrofit2.http.GET

interface EventApiService {

    @GET("/events")
    suspend fun getEvents(): List<Event>
}