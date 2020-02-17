package com.example.myapplication.ArticlesFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody

class ArticlesViewModel : ViewModel() {
    private var repositoryHelper: ArticleRepository = ArticleRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseBody>


    fun getArticles() {
        mutableLiveData = repositoryHelper.getArticles()

    }

    fun getData(): MutableLiveData<ResponseBody> {
        return mutableLiveData
    }

}
