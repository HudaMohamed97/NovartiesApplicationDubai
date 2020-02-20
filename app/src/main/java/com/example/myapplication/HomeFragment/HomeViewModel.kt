package com.example.myapplication.HomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.LoginFragment.HomeRepository
import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.EventModels.SingleEventResponse
import com.example.myapplication.Models.LocationResponse

class HomeViewModel : ViewModel() {
    private var repositoryHelper: HomeRepository = HomeRepository()
    private lateinit var mutableLiveData: MutableLiveData<LocationResponse>
    private lateinit var singleEventLiveData: MutableLiveData<LocationResponse>


    fun getData(): MutableLiveData<LocationResponse> {
        return mutableLiveData
    }

    fun getLocation(accessToken: String) {
        mutableLiveData = repositoryHelper.getLocation(accessToken)
    }

}
