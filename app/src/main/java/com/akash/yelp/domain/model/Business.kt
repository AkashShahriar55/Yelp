package com.akash.yelp.domain.model

import com.google.gson.annotations.SerializedName
import com.akash.yelp.domain.model.Business

/**
 * Business model from the Yelp v3 API.
 * @see [Yelp API Business Search](https://www.yelp.ca/developers/documentation/v3/business_search)
 */
class Business {
    @SerializedName("name")
    val name: String? = null

    @SerializedName("categories")
    val categories: List<Category>? = null

    @SerializedName("image_url")
    val imageUrl: String? = null

    @SerializedName("rating")
    val rating: Double? = null

    @SerializedName("location")
    val location: Location? = null
}