package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.EventModels.EventsResponse
import com.imagin.myapplication.Models.EventModels.SingleEventResponse


class EventViewModel : ViewModel() {
    private var repositoryHelper: HomeRepository = HomeRepository()
    private lateinit var mutableLiveData: MutableLiveData<EventsResponse>
    private lateinit var SingleEventLiveData: MutableLiveData<SingleEventResponse>





    fun getData(): MutableLiveData<EventsResponse> {
        return mutableLiveData
    }

    fun getSingleEventData(): MutableLiveData<SingleEventResponse> {
        return SingleEventLiveData
    }


}







