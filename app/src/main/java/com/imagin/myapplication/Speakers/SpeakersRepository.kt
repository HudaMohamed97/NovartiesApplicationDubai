package com.imagin.myapplication.Speakers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imagin.myapplication.Models.SpeakersResponseModel
import com.imagin.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpeakersRepository {
    fun getSpeakers(accessToken: String): MutableLiveData<SpeakersResponseModel> {
        val userData = MutableLiveData<SpeakersResponseModel>()
        Webservice.getInstance().api.getSpeakers(accessToken)
            .enqueue(object : Callback<SpeakersResponseModel> {
                override fun onResponse(
                    call: Call<SpeakersResponseModel>, response: Response<SpeakersResponseModel>
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

                override fun onFailure(call: Call<SpeakersResponseModel>, t: Throwable) {
                    userData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return userData
    }

    fun getSpeakersPaginated(
        pagenumber: Int,
        accessToken: String
    ): MutableLiveData<SpeakersResponseModel> {
        val userData = MutableLiveData<SpeakersResponseModel>()
        Webservice.getInstance().api.getSpeakersPaginated(pagenumber, accessToken)
            .enqueue(object : Callback<SpeakersResponseModel> {
                override fun onResponse(
                    call: Call<SpeakersResponseModel>, response: Response<SpeakersResponseModel>
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

                override fun onFailure(call: Call<SpeakersResponseModel>, t: Throwable) {
                    userData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return userData
    }


}


