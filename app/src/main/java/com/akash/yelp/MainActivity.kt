package com.akash.yelp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.akash.yelp.domain.model.Business
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: SearchResultViewModel
    private lateinit var mEtLocation: EditText
    private val term = "pizza and beer"
    private lateinit var mBtSearch: Button
    private lateinit var mLlSearchResultList: LinearLayout
    private lateinit var inflater: LayoutInflater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchResultViewModel::class.java)
        inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        initView()
        mViewModel.searchYelpApi(term, mEtLocation.text.toString())
    }

    override fun onResume() {
        mLlSearchResultList.removeAllViews()
        buildViewForSearchYelpApi()
        super.onResume()
    }

    private fun initView() {
        mEtLocation = findViewById(R.id.et_location)
        mBtSearch = findViewById(R.id.button)
        mBtSearch.setOnClickListener(
            View.OnClickListener { v: View? ->

                hideSoftKeyboard(mEtLocation, this)
                mViewModel.searchYelpApi(
                    term,
                    mEtLocation.getText().toString()
                )
            }
        )
        mLlSearchResultList = findViewById(R.id.ll_search_result_list)
        mEtLocation.setText(getString(R.string.toronto))
    }

    private fun buildViewForSearchYelpApi() {
        mViewModel.businessLiveData.observe(this) { response ->
            if (response != null) {
                mViewModel.buildCategoryMap(mViewModel.businessList)
                buildViewForCategory()
            }
        }
    }

    private fun buildViewForCategory() {
        mLlSearchResultList.removeAllViews()
        val categoryList: List<String> = mViewModel.categoryList
        val categoryMap: Map<String, List<Business>> = mViewModel.categoryMap
        for (category in categoryList) {
            val view: View =
                inflater.inflate(R.layout.row_category_header, mLlSearchResultList, false)
            val num = categoryMap[category]!!.size.toString()
            (view.findViewById<View>(R.id.tv_category) as TextView).text =
                category + getString(R.string.colon) + num
            mLlSearchResultList.addView(view)
            if (categoryMap[category] != null) {
                categoryMap[category]?.let { buildViewForBusinesses(it) }
            }
        }
    }

    private fun buildViewForBusinesses(businessList: List<Business>) {
        for (business in businessList) {
            val view: View = inflater.inflate(R.layout.row_business, mLlSearchResultList, false)
            (view.findViewById<View>(R.id.tv_business_name) as TextView).setText(business.name)
            (view.findViewById<View>(R.id.tv_address) as TextView).text =
                getString(R.string.address) + business.location?.address1
            (view.findViewById<View>(R.id.tv_business_category) as TextView).text =
                getString(R.string.category) + business.categories.toString()
                    .substring(1, business.categories.toString().length - 1)
            (view.findViewById<View>(R.id.rb_ratingBar) as RatingBar).rating =
                business.rating!!.toFloat()
            Glide.with(view.context).load(business.imageUrl)
                .into(view.findViewById<View>(R.id.iv_imageView) as ImageView)
            view.setOnClickListener { view ->
                hideSoftKeyboard(mEtLocation, this@MainActivity)
                if ((view.findViewById<View>(R.id.tv_business_category) as TextView).visibility == View.GONE) {
                    (view.findViewById<View>(R.id.tv_business_category) as TextView).visibility =
                        View.VISIBLE
                    (view.findViewById<View>(R.id.tv_address) as TextView).visibility =
                        View.VISIBLE
                    (view.findViewById<View>(R.id.iv_imageView) as ImageView).visibility =
                        View.VISIBLE
                    (view.findViewById<View>(R.id.rb_ratingBar) as RatingBar).visibility =
                        View.VISIBLE
                } else {
                    (view.findViewById<View>(R.id.tv_business_category) as TextView).visibility =
                        View.GONE
                    (view.findViewById<View>(R.id.tv_address) as TextView).visibility =
                        View.GONE
                    (view.findViewById<View>(R.id.iv_imageView) as ImageView).visibility =
                        View.GONE
                    (view.findViewById<View>(R.id.rb_ratingBar) as RatingBar).visibility =
                        View.GONE
                }
            }
            mLlSearchResultList.addView(view)
        }
    }

    private fun hideSoftKeyboard(view: View?, mActivity: Activity) {
        val inputMethodManager =
            mActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            view!!.windowToken,
            InputMethodManager.SHOW_FORCED
        )
    }
}