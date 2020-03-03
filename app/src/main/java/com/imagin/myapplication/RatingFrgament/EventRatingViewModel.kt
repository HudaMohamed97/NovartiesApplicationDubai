package com.imagin.myapplication.RatingFrgament

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.AgendaRatingReponseModeldata
import com.imagin.myapplication.Models.SubmitModel

class EventRatingViewModel : ViewModel() {
    private var repositoryHelper: RatingEventRepository = RatingEventRepository()
    private lateinit var submitMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var submitMultipleLiveData: MutableLiveData<SubmitModel>
    private lateinit var mutableLiveData: MutableLiveData<AgendaRatingReponseModeldata>


    fun submitRate(
        rate: Int, sessionId: Int, accessToken: String
    ) {
        submitMutableLiveData =
            repositoryHelper.submitRatingSession(sessionId, rate, accessToken)

    }

    fun getDataSubmitRate(): MutableLiveData<SubmitModel> {
        return submitMutableLiveData
    }

    fun submitMutipleRate(
        rate: ArrayList<Int>, sessionId: Int, accessToken: String
    ) {
        submitMultipleLiveData =
            repositoryHelper.submitMutipleRatingSession(rate, sessionId, accessToken)

    }

    fun getDataMutipleSubmitRate(): MutableLiveData<SubmitModel> {
        return submitMultipleLiveData
    }


    fun getData(): MutableLiveData<AgendaRatingReponseModeldata> {
        return mutableLiveData
    }

    fun getSessionData(accessToken: String) {
        mutableLiveData = repositoryHelper.getSessions(accessToken)
    }
}
