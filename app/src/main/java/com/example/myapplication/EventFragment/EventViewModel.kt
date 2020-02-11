package com.example.myapplication.LoginFragment

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.EventModels.EventsResponse


class EventViewModel : ViewModel() {
    private var repositoryHelper: EventRepository = EventRepository()
    private lateinit var mutableLiveData: MutableLiveData<EventsResponse>


    fun getEvents(type: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getEvents(1, accessToken)

    }


    fun getData(): MutableLiveData<EventsResponse> {
        return mutableLiveData


    }


}







