package com.example.myapplication.SessionsFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SessionsResponse
import com.example.myapplication.Models.submitModel
import com.example.myapplication.NetworkLayer.Webservice
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionRepository {

    fun getSessions(accessToken: String): MutableLiveData<SessionsResponse> {
        val userData = MutableLiveData<SessionsResponse>()
        Webservice.getInstance().api.getSessions(accessToken)
            .enqueue(object : Callback<SessionsResponse> {
                override fun onResponse(
                    call: Call<SessionsResponse>,
                    response: Response<SessionsResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SessionsResponse>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

    fun submitAttendance(
        state: Int,
        sessionId: Int,
        accessToken: String
    ): MutableLiveData<submitModel> {
        val Data = MutableLiveData<submitModel>()
        val body = mapOf(
            "attended" to state.toString()
        )
        Webservice.getInstance().api.submitAttendance(body, sessionId, accessToken)
            .enqueue(object : Callback<submitModel> {
                override fun onResponse(
                    call: Call<submitModel>,
                    response: Response<submitModel>
                ) {
                    if (response.isSuccessful) {
                        Data.value = response.body()
                    } else {
                        Data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<submitModel>, t: Throwable) {
                    Data.value = null
                }
            })
        return Data
    }
}