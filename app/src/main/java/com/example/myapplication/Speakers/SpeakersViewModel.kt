package com.example.myapplication.Speakers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SpeakersResponseModel

class SpeakersViewModel : ViewModel() {
    private var repositoryHelper: SpeakersRepository = SpeakersRepository()
    private lateinit var mutableLiveData: MutableLiveData<SpeakersResponseModel>
    private lateinit var paginatedMutableLiveData: MutableLiveData<SpeakersResponseModel>


    fun getSpeakers(accessToken: String) {
        mutableLiveData = repositoryHelper.getSpeakers(accessToken)

    }

    fun getData(): MutableLiveData<SpeakersResponseModel> {
        return mutableLiveData
    }

    fun getSpeakersPaginated(pageNumber: Int, accessToken: String) {
        paginatedMutableLiveData = repositoryHelper.getSpeakersPaginated(pageNumber, accessToken)

    }

    fun getDataPaginated(): MutableLiveData<SpeakersResponseModel> {
        return paginatedMutableLiveData
    }
}