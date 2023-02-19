package com.akash.yelp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import com.akash.yelp.domain.model.SearchResult;
import com.akash.yelp.domain.services.YelpApi;
import com.akash.yelp.domain.services.YelpRetrofit;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test_yelp_api() throws IOException {
        Call<SearchResult> resultCall;
        resultCall = new YelpRetrofit()
                .getRetrofitInstance()
                .create(YelpApi.class)
                .search("", "Toronto");
        Response<SearchResult> res = resultCall.execute();
        assertThat(res.code(), is(200));
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(res.body()));
    }
}