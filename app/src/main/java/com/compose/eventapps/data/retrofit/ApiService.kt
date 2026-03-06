package com.compose.eventapps.data.retrofit

import com.compose.eventapps.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  @GET("/events")
  fun getRestaurant(
    @Query("active") active: Int,
    @Query("q") q: String,
    @Query("limit") limit: Int
  ): Call<EventResponse>

  @GET("/events/{id}")
  fun getRestaurantDetail(
    @Path("id") id: String
  ): Call<EventResponse>
}