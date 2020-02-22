package com.example.myapplication.PostsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.PostsModelResponse
import com.example.myapplication.Models.SubmitModel


class PostViewModel : ViewModel() {
    private var repositoryHelper: PostsRepository = PostsRepository()
    private lateinit var mutableLiveData: MutableLiveData<PostsModelResponse>
    private lateinit var addMutableLiveData: MutableLiveData<SubmitModel>


    fun getPosts(pageId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getPosts(pageId, accessToken)

    }

    fun getData(): MutableLiveData<PostsModelResponse> {
        return mutableLiveData
    }

    fun setPost(
        content: String,
        imageFile: String, accessToken: String
    ) {
        addMutableLiveData = repositoryHelper.setPost(content, imageFile, accessToken)

    }

    fun getDataAddPost(): MutableLiveData<SubmitModel> {
        return addMutableLiveData
    }


}
