package com.akash.yelp.domain.model

import com.google.gson.annotations.SerializedName
import com.akash.yelp.domain.model.Business

class SearchResult {
    @SerializedName("total")
    val total = 0

    @SerializedName("businesses")
    val businesses: List<Business>? = null
}