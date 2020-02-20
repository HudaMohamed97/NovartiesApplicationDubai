package com.example.myapplication.VotingFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.PollModel

class PollViewModel : ViewModel() {
    private var repositoryHelper: PollRepository = PollRepository()
    private lateinit var mutableLiveData: MutableLiveData<PollModel>


    fun getPolls(accessToken: String) {
        mutableLiveData = repositoryHelper.getPolls(accessToken)

    }

    fun getData(): MutableLiveData<PollModel> {
        return mutableLiveData
    }

}
