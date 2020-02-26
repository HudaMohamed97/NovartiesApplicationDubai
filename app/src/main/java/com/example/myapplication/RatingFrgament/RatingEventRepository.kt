package com.example.myapplication.RatingFrgament

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.AgendaRatingReponseModeldata
import com.example.myapplication.Models.SubmitModel
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingEventRepository {

    fun getSessions(accessToken: String): MutableLiveData<AgendaRatingReponseModeldata> {
        val userData = MutableLiveData<AgendaRatingReponseModeldata>()
        Webservice.getInstance().api.getAgendaRating(accessToken)
            .enqueue(object : Callback<AgendaRatingReponseModeldata> {
                override fun onResponse(
                    call: Call<AgendaRatingReponseModeldata>,
                    response: Response<AgendaRatingReponseModeldata>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<AgendaRatingReponseModeldata>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }


    fun submitRatingSession(
        rate: Int,
        sessionId: Int,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val body = mapOf(
            "rate" to rate.toString()
        )
        val pollData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.submitEventRate(sessionId, body, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        pollData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            pollData.value = SubmitModel("error", "error")
                        } else {
                            pollData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    pollData.value = null
                }
            })
        return pollData
    }

}
