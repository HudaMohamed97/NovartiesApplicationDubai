package com.example.myapplication.QuestionsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.QuestionsModelResponse
import com.example.myapplication.Models.SingelQuestionModel
import com.example.myapplication.Models.SpeakersResponseModel
import com.example.myapplication.Models.SubmitModel

class QuestionsViewModel : ViewModel() {
    private var repositoryHelper: QuestionRepository = QuestionRepository()
    private lateinit var mutableLiveData: MutableLiveData<QuestionsModelResponse>
    private lateinit var submitMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var submitRateLiveData: MutableLiveData<SubmitModel>
    private lateinit var singelQuestionLiveData: MutableLiveData<SingelQuestionModel>


    fun getQuestions(accessToken: String) {
        mutableLiveData = repositoryHelper.getQuestions(accessToken)

    }

    fun getData(): MutableLiveData<QuestionsModelResponse> {
        return mutableLiveData
    }

    fun submitQuestions(questionId: Int, answerId: Int, accessToken: String) {
        submitMutableLiveData = repositoryHelper.submitAnswer(questionId, answerId, accessToken)

    }

    fun getSubmitData(): MutableLiveData<SubmitModel> {
        return submitMutableLiveData
    }

    fun submitRateOptionQuestions(questionId: Int, rate: Int, accessToken: String) {
        submitRateLiveData = repositoryHelper.submitRateAnswer(questionId, rate, accessToken)

    }

    fun getSubmitDataRate(): MutableLiveData<SubmitModel> {
        return submitRateLiveData
    }

    fun getSingelQuestions(questionId: Int, accessToken: String) {
        singelQuestionLiveData = repositoryHelper.getSingeQuestion(questionId, accessToken)

    }

    fun getSingelQuestionData(): MutableLiveData<SingelQuestionModel> {
        return singelQuestionLiveData
    }

}
