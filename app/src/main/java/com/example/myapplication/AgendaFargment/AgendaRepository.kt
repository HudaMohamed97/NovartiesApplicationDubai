package com.example.myapplication.AgendaFargment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.AgendaModelResponse
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaRepository {
    fun getAgenda(accessToken: String): MutableLiveData<AgendaModelResponse> {
        val userData = MutableLiveData<AgendaModelResponse>()
        Webservice.getInstance().api.getAgenda(accessToken)
            .enqueue(object : Callback<AgendaModelResponse> {
                override fun onResponse(
                    call: Call<AgendaModelResponse>, response: Response<AgendaModelResponse>
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

                override fun onFailure(call: Call<AgendaModelResponse>, t: Throwable) {
                    userData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return userData
    }

}
