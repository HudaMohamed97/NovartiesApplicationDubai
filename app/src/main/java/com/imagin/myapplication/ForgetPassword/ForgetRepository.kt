package com.imagin.myapplication.ForgetPassword

import androidx.lifecycle.MutableLiveData
import com.imagin.myapplication.Models.SubmitModel
import com.imagin.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetRepository {
    fun resetEmail(
        email: String
    ): MutableLiveData<SubmitModel> {

        val answerData = MutableLiveData<SubmitModel>()
        val body = mapOf(
            "email" to email
        )
        Webservice.getInstance().api.reset(body)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        answerData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            answerData.value = SubmitModel("NoAccount", "NoAccount")
                        } else if (response.code() == 422) {
                            answerData.value = SubmitModel("NotValid", "NotValid")

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