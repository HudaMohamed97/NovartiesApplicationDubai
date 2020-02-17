package com.example.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.EventModels.SingleEventResponse


class EventViewModel : ViewModel() {
    private var repositoryHelper: HomeRepository = HomeRepository()
    private lateinit var mutableLiveData: MutableLiveData<EventsResponse>
    private lateinit var SingleEventLiveData: MutableLiveData<SingleEventResponse>


    fun getEvents(type: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getEvents(1, accessToken)

    }

    fun getSingleEvent(eventId: Int, type: Int, accessToken: String) {
        SingleEventLiveData = repositoryHelper.getSingleEvent(eventId, 1, accessToken)

    }


    fun getData(): MutableLiveData<EventsResponse> {
        return mutableLiveData
    }

    fun getSingleEventData(): MutableLiveData<SingleEventResponse> {
        return SingleEventLiveData
    }


}







