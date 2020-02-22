package com.example.myapplication.PostsFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.PostsModelResponse
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsRepository {
    fun getPosts(pageId: Int, accessToken: String): MutableLiveData<PostsModelResponse> {
        val userData = MutableLiveData<PostsModelResponse>()
        Webservice.getInstance().api.getPosts(pageId, accessToken)
            .enqueue(object : Callback<PostsModelResponse> {
                override fun onResponse(
                    call: Call<PostsModelResponse>, response: Response<PostsModelResponse>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PostsModelResponse>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

}
