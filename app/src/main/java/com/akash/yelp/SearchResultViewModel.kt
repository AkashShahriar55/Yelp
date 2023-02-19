package com.akash.yelp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.akash.yelp.domain.model.Business
import androidx.lifecycle.MutableLiveData
import com.akash.yelp.domain.model.SearchResult
import com.akash.yelp.domain.services.YelpRetrofit
import com.akash.yelp.domain.services.YelpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchResultViewModel : ViewModel() {
    var categoryMap: MutableMap<String, MutableList<Business>> = mutableMapOf()
    val businessLiveData = MutableLiveData<List<Business>>()
    private val TAG = "Search"
    fun searchYelpApi(term: String?, location: String?) {
        YelpRetrofit()
            .retrofitInstance
            .create(YelpApi::class.java)
            .search(term, location)!!.enqueue(object : Callback<SearchResult?> {
                override fun onResponse(
                    call: Call<SearchResult?>,
                    response: Response<SearchResult?>
                ) {
                    Log.w(TAG, "onResponse")
                    if (!response.isSuccessful || response.body() == null) {
                        Log.w(TAG, "No valid response")
                        return
                    }
                    businessLiveData.postValue(response.body()!!.businesses)
                }

                override fun onFailure(call: Call<SearchResult?>, t: Throwable) {
                    Log.w(TAG, "OnFailure" + t.message)
                }
            })
    }

    fun buildCategoryMap(businessList: List<Business>) {
        categoryMap.clear()
        for (business in businessList) {
            val categoryList = business.categories
            for (category in categoryList!!) {
                if (categoryMap.get(category.title) == null) {
                    categoryMap[category.title!!] = ArrayList()
                }
                categoryMap.get(category.title)!!.add(business)
            }
        }
    }

    val categoryList: List<String>
        get() {
            return ArrayList(
                categoryMap.keys
            )
        }
    val businessList: List<Business>
        get() = businessLiveData.value!!

}