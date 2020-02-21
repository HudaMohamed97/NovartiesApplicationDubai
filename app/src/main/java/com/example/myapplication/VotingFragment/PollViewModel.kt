package com.example.myapplication.VotingFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.PollModel
import com.example.myapplication.Models.SingelPollModel
import com.example.myapplication.Models.SubmitModel

class PollViewModel : ViewModel() {
    private var repositoryHelper: PollRepository = PollRepository()
    private lateinit var mutableLiveData: MutableLiveData<PollModel>
    private lateinit var mutableLiveDataPoll: MutableLiveData<SingelPollModel>
    private lateinit var submitedLiveData: MutableLiveData<SubmitModel>


    fun getPolls(accessToken: String) {
        mutableLiveData = repositoryHelper.getPolls(accessToken)

    }

    fun getData(): MutableLiveData<PollModel> {
        return mutableLiveData
    }

    fun getSingePolls(pollId: Int, accessToken: String) {
        mutableLiveDataPoll = repositoryHelper.getSingePoll(pollId, accessToken)

    }

    fun getSinglePollData(): MutableLiveData<SingelPollModel> {
        return mutableLiveDataPoll
    }

    fun callSubmitOption(pollId: Int, pollOptionId: Int, accessToken: String) {
        submitedLiveData = repositoryHelper.submitVotedPoll(pollId, pollOptionId, accessToken)

    }

    fun getSubmittedStatues(): MutableLiveData<SubmitModel> {
        return submitedLiveData
    }

}
