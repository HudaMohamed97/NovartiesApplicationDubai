package com.imagin.myapplication.RatingFrgament

import androidx.lifecycle.MutableLiveData
import com.imagin.myapplication.Models.SubmitModel
import com.imagin.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingRepository {
    fun submitRatingSession(
        rate: Int,
        sessionId: Int,
        comment: String,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val body = mapOf(
            "rate" to rate.toString(),
            "comment" to comment
        )
        val pollData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.submitSessionsRate(sessionId, body, accessToken)
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