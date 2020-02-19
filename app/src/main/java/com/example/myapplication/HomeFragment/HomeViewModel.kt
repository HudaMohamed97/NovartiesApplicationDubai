package com.example.myapplication.HomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.LoginFragment.HomeRepository
import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.EventModels.SingleEventResponse

class HomeViewModel : ViewModel() {
    private var repositoryHelper: HomeRepository = HomeRepository()
    private lateinit var mutableLiveData: MutableLiveData<EventsResponse>
    private lateinit var singleEventLiveData: MutableLiveData<SingleEventResponse>


    fun getData(): MutableLiveData<EventsResponse> {
        return mutableLiveData
    }

    fun getSingleEventData(): MutableLiveData<SingleEventResponse> {
        return singleEventLiveData
    }

}
