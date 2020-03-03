package com.imagin.myapplication.VotingFragment

import androidx.lifecycle.MutableLiveData
import com.imagin.myapplication.Models.PollModel
import com.imagin.myapplication.Models.SingelPollModel
import com.imagin.myapplication.Models.SubmitModel
import com.imagin.myapplication.NetworkLayer.Webservice
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
    ): MutableLiveData<SubmitModel> {
        val body = mapOf(
            "poll_id" to pollId.toString(),
            "poll_options_id" to pollOptionId.toString()
        )
        val pollData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.submitPolls(body, accessToken)
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
