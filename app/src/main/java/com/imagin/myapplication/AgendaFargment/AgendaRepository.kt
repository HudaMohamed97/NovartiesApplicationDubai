package com.imagin.myapplication.AgendaFargment

import androidx.lifecycle.MutableLiveData
import com.imagin.myapplication.Models.AgendaData
import com.imagin.myapplication.Models.AgendaModelResponse
import com.imagin.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaRepository {
    fun getAgenda(day: Int, accessToken: String): MutableLiveData<AgendaModelResponse> {
        val userData = MutableLiveData<AgendaModelResponse>()
        Webservice.getInstance().api.getAgenda(day, accessToken)
            .enqueue(object : Callback<AgendaModelResponse> {
                override fun onResponse(
                    call: Call<AgendaModelResponse>, response: Response<AgendaModelResponse>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        if (response.code() == 404) {
                            AgendaModelResponse(AgendaData(-1, "", emptyList()),0)
                        } else {
                            userData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<AgendaModelResponse>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

}
