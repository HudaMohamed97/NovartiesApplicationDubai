package com.imagin.myapplication.HomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.LoginFragment.HomeRepository
import com.imagin.myapplication.Models.LocationResponse

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
