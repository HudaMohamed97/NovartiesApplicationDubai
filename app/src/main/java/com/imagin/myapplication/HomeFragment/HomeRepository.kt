package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import com.imagin.myapplication.Models.LocationResponse
import com.imagin.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    fun getLocation(
        accessToken: String
    ): MutableLiveData<LocationResponse> {
        val singleEventData = MutableLiveData<LocationResponse>()
        Webservice.getInstance().api.getLocation(accessToken)
            .enqueue(object : Callback<LocationResponse> {
                override fun onResponse(
                    call: Call<LocationResponse>,
                    response: Response<LocationResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        singleEventData.value = response.body()
                    } else {
                        singleEventData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                    singleEventData.value = null
                }
            })
        return singleEventData
    }


}



