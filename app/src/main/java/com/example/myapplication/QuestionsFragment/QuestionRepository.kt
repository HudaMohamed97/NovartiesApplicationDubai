package com.example.myapplication.QuestionsFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.*
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionRepository {
    fun getQuestions(accessToken: String): MutableLiveData<QuestionsModelResponse> {
        val userData = MutableLiveData<QuestionsModelResponse>()
        Webservice.getInstance().api.getQuestions(accessToken)
            .enqueue(object : Callback<QuestionsModelResponse> {
                override fun onResponse(
                    call: Call<QuestionsModelResponse>, response: Response<QuestionsModelResponse>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<QuestionsModelResponse>, t: Throwable) {
                    userData.value = null
                }
            })
        return userData
    }


    fun getSingeQuestion(
        questionId: Int,
        accessToken: String
    ): MutableLiveData<SingelQuestionModel> {
        val pollData = MutableLiveData<SingelQuestionModel>()
        Webservice.getInstance().api.getSingeQuestion(questionId, accessToken)
            .enqueue(object : Callback<SingelQuestionModel> {
                override fun onResponse(
                    call: Call<SingelQuestionModel>, response: Response<SingelQuestionModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        pollData.value = response.body()
                    } else {
                        pollData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SingelQuestionModel>, t: Throwable) {
                    pollData.value = null
                }
            })
        return pollData
    }

    fun submitAnswer(
        questionId: Int,
        answerOptionId: Int,
        accessToken: String
    ): MutableLiveData<SubmitModel> {

        val answerData = MutableLiveData<SubmitModel>()
        val body = mapOf(
            "practice_id" to questionId.toString(),
            "practice_option_id" to answerOptionId.toString()
        )
        Webservice.getInstance().api.submitAnswer(body, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        answerData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            answerData.value = SubmitModel("error", "error")
                        } else {
                            answerData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    answerData.value = null
                }
            })
        return answerData
    }

    fun submitRateAnswer(
        questionId: Int,
        answerOptionId: Int,
        accessToken: String
    ): MutableLiveData<SubmitModel> {

        val answerData = MutableLiveData<SubmitModel>()
        val body = mapOf(
            "practice_option_id" to questionId.toString(),
            "rate" to answerOptionId.toString()
        )
        Webservice.getInstance().api.submitRateAnswer(body, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        answerData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            answerData.value = SubmitModel("error", "error")
                        } else {
                            answerData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    answerData.value = null
                }
            })
        return answerData
    }

}
