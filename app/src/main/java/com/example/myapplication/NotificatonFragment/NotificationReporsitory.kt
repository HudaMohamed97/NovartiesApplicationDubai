package com.example.myapplication.NotificatonFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.NotificationModelResponse
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationReporsitory {
    fun getNotification(
        pageId: Int,
        accessToken: String
    ): MutableLiveData<NotificationModelResponse> {
        val userData = MutableLiveData<NotificationModelResponse>()
        Webservice.getInstance().api.getNotification(pageId, accessToken)
            .enqueue(object : Callback<NotificationModelResponse> {
                override fun onResponse(
                    call: Call<NotificationModelResponse>,
                    response: Response<NotificationModelResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<NotificationModelResponse>, t: Throwable) {
                    userData.value = null

                }
            })
        return userData
    }

}
