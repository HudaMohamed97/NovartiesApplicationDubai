package com.example.myapplication.VotingFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.PollModel
import com.example.myapplication.Models.SingelPollModel
import com.example.myapplication.Models.submitModel
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PollRepository {
    fun getPolls(accessToken: String): MutableLiveData<PollModel> {
        val userData = MutableLiveData<PollModel>()
        Webservice.getInstance().api.getPolls(accessToken)
            .enqueue(object : Callback<PollModel> {
                override fun onResponse(
                    call: Call<PollModel>, response: Response<PollModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PollModel>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }

    fun getSingePoll(pollId: Int, accessToken: String): MutableLiveData<SingelPollModel> {
        val pollData = MutableLiveData<SingelPollModel>()
        Webservice.getInstance().api.getSingePolls(pollId, accessToken)
            .enqueue(object : Callback<SingelPollModel> {
                override fun onResponse(
                    call: Call<SingelPollModel>, response: Response<SingelPollModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        pollData.value = response.body()
                    } else {
                        pollData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SingelPollModel>, t: Throwable) {
                    pollData.value = null
                }
            })
        return pollData
    }

    fun submitVotedPoll(
        pollId: Int,
        pollOptionId: Int,
        accessToken: String
    ): MutableLiveData<submitModel> {
        val body = mapOf(
            "poll_id" to pollId.toString(),
            "poll_options_id" to pollOptionId.toString()
        )
        val pollData = MutableLiveData<submitModel>()
        Webservice.getInstance().api.submitPolls(body, accessToken)
            .enqueue(object : Callback<submitModel> {
                override fun onResponse(
                    call: Call<submitModel>, response: Response<submitModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        pollData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            pollData.value = submitModel("error", "error")
                        } else {
                            pollData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<submitModel>, t: Throwable) {
                    pollData.value = null
                }
            })
        return pollData
    }
}
