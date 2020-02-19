package com.example.myapplication.LoginFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.EventModels.SingleEventResponse
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {



    fun getSingleEvent(
        eventId: Int,
        type: Int,
        accessToken: String
    ): MutableLiveData<SingleEventResponse> {
        val singleEventData = MutableLiveData<SingleEventResponse>()
        Webservice.getInstance().api.getSingleEventData(eventId, type, accessToken)
            .enqueue(object : Callback<SingleEventResponse> {
                override fun onResponse(
                    call: Call<SingleEventResponse>,
                    response: Response<SingleEventResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        singleEventData.value = response.body()
                    } else {
                        Log.i(
                            "hhhhhh",
                            "on failuer from sucess" + response.message() + response.body()
                        )
                        singleEventData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SingleEventResponse>, t: Throwable) {
                    singleEventData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return singleEventData
    }


}



