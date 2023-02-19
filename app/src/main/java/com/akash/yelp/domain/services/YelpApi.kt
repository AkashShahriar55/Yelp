package com.akash.yelp.domain.services

import com.akash.yelp.domain.model.SearchResult
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query
import kotlin.Throws

interface YelpApi {
    @GET("/v3/businesses/search")
    fun search(
        @Query("term") term: String?,
        @Query("location") location: String?
    ): Call<SearchResult?>?
}