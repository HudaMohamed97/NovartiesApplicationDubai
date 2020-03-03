package com.imagin.myapplication.RatingFrgament

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.SubmitModel

class RatingFragmentViewModel : ViewModel() {
    private var repositoryHelper: RatingRepository = RatingRepository()
    private lateinit var mutableLiveData: MutableLiveData<SubmitModel>


    fun submitRate(
        rate: Int, sessionId: Int,
        comment: String, accessToken: String
    ) {
        mutableLiveData =
            repositoryHelper.submitRatingSession(sessionId, rate, comment, accessToken)

    }

    fun getData(): MutableLiveData<SubmitModel> {
        return mutableLiveData
    }


}