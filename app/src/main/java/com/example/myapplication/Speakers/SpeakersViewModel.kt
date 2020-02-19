package com.example.myapplication.Speakers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SpeakersResponseModel

class SpeakersViewModel:ViewModel() {
    private var repositoryHelper: SpeakersRepository = SpeakersRepository()
    private lateinit var mutableLiveData: MutableLiveData<SpeakersResponseModel>



    fun getSpeakers(accessToken:String) {
        mutableLiveData = repositoryHelper.getSpeakers(accessToken)

    }

    fun getData(): MutableLiveData<SpeakersResponseModel> {
        return mutableLiveData
    }
}