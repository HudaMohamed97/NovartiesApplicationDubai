package com.example.myapplication.ArticlesFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.NetworkLayer.Webservice
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {

    fun getArticles(): MutableLiveData<ResponseBody> {
        val userData = MutableLiveData<ResponseBody>()
        Webservice.getInstance().api.getArticles().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    response.raw()
                    userData.value = response.body()
                } else {
                    Log.i(
                        "hhhhhh",
                        "on failuer from sucess" + response.message() + response.body()
                    )
                    userData.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                userData.value = null
                Log.i("hhhhhh", "on fail to get events")
                Log.i("hhhhhh", "on fail" + t.message)

            }
        })
        return userData
    }

}