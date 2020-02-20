package com.example.myapplication.VotingFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.PollModel
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
                        Log.i(
                            "hhhhhh",
                            "on failuer from sucess" + response.message() + response.body()
                        )
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PollModel>, t: Throwable) {
                    userData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return userData
    }

    fun getSingelPoll(pollId: Int, accessToken: String): MutableLiveData<PollModel> {
        val pollData = MutableLiveData<PollModel>()
        Webservice.getInstance().api.getSingelPolls(pollId, accessToken)
            .enqueue(object : Callback<PollModel> {
                override fun onResponse(
                    call: Call<PollModel>, response: Response<PollModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        pollData.value = response.body()
                    } else {
                        Log.i(
                            "hhhhhh",
                            "on failuer from sucess" + response.message() + response.body()
                        )
                        pollData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PollModel>, t: Throwable) {
                    pollData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })
        return pollData
    }

    fun submitVotedPoll(
        pollId: Int,
        pollOptionId: Int,
        accessToken: String
    ): MutableLiveData<PollModel> {
        val body = mapOf(
            "poll_id" to pollId.toString(),
            "poll_options_id" to pollOptionId.toString()
        )
        val pollData = MutableLiveData<PollModel>()
        Webservice.getInstance().api.submitPolls(body, accessToken)
            .enqueue(object : Callback<PollModel> {
                override fun onResponse(
                    call: Call<PollModel>, response: Response<PollModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        pollData.value = response.body()
                    } else {
                        Log.i(
                            "hhhhhh",
                            "on failuer from sucess" + response.message() + response.body()
                        )
                        pollData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PollModel>, t: Throwable) {
                    pollData.value = null
                    Log.i("hhhhhh", "on fail to get events")
                    Log.i("hhhhhh", "on fail" + t.message)
                }
            })
        return pollData
    }
}
