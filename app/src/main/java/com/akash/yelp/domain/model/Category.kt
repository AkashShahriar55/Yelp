package com.akash.yelp.domain.model

import com.google.gson.annotations.SerializedName
import com.akash.yelp.domain.model.Business

class Category {
    @SerializedName("title")
    val title: String? = null
    override fun toString(): String {
        return title!!
    }
}