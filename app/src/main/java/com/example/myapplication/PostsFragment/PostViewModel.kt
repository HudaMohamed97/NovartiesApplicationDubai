package com.example.myapplication.PostsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.PostsModelResponse


class PostViewModel : ViewModel() {
    private var repositoryHelper: PostsRepository = PostsRepository()
    private lateinit var mutableLiveData: MutableLiveData<PostsModelResponse>


    fun getPosts(pageId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getPosts(pageId, accessToken)

    }

    fun getData(): MutableLiveData<PostsModelResponse> {
        return mutableLiveData
    }


}
