package com.example.myapplication.AgendaFargment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.AgendaModelResponse
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaRepository {
    fun getAgenda(day: Int, accessToken: String): MutableLiveData<AgendaModelResponse> {
        val userData = MutableLiveData<AgendaModelResponse>()
        Webservice.getInstance().api.getAgenda(day,accessToken)
            .enqueue(object : Callback<AgendaModelResponse> {
                override fun onResponse(
                    call: Call<AgendaModelResponse>, response: Response<AgendaModelResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<AgendaModelResponse>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

}
