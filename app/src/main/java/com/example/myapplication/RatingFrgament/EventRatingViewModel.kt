package com.example.myapplication.RatingFrgament

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.AgendaRatingReponseModeldata
import com.example.myapplication.Models.SubmitModel

class EventRatingViewModel : ViewModel() {
    private var repositoryHelper: RatingEventRepository = RatingEventRepository()
    private lateinit var submitMutableLiveData: MutableLiveData<SubmitModel>
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


    fun getData(): MutableLiveData<AgendaRatingReponseModeldata> {
        return mutableLiveData
    }

    fun getSessionData(accessToken: String) {
        mutableLiveData = repositoryHelper.getSessions(accessToken)
    }
}
