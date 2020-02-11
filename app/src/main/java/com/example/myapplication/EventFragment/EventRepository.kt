package com.example.myapplication.LoginFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository {

    fun getEvents(type: Int, accessToken: String): MutableLiveData<EventsResponse> {
        val userData = MutableLiveData<EventsResponse>()
        Webservice.getInstance().api.getEvents(type, accessToken)
            .enqueue(object : Callback<EventsResponse> {
                override fun onResponse(
                    call: Call<EventsResponse>,
                    response: Response<EventsResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        Log.i(
                            "hhhhhh",
                            "on failuer from sucess" + response.message() + response.body()
                        )
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                    userData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return userData
    }


}



