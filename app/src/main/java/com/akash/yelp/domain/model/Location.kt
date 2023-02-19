package com.akash.yelp.domain.model

import com.google.gson.annotations.SerializedName
import com.akash.yelp.domain.model.Business

class Location {
    @SerializedName("address1")
    val address1: String? = null

    @SerializedName("zip_code")
    val zip_code: String? = null
}