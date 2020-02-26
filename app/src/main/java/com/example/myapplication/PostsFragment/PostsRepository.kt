package com.example.myapplication.PostsFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.PostsModelResponse
import com.example.myapplication.Models.SubmitModel
import com.example.myapplication.NetworkLayer.Webservice
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

    fun setPost(
        content: String,
        imageFile: String,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val userData = MutableLiveData<SubmitModel>()
        val content = RequestBody.create(MediaType.parse("multipart/form-data"), content)


        var fileToUpload: MultipartBody.Part? = null
        try {
            val file = File(imageFile)
            if (file.exists()) {
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                fileToUpload = MultipartBody.Part.createFormData("photo", file?.name, requestFile)
            } else {
                val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                fileToUpload = MultipartBody.Part.createFormData("photo", "", attachmentEmpty)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        Webservice.getInstance().api.addPost(content, fileToUpload!!, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

    fun deletePost(
        postId: Int,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val userData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.deletePost(postId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

}
