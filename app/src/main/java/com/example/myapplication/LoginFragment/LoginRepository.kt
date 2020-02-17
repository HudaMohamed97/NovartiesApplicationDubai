package com.example.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.LoginRequestModel
import com.example.myapplication.Models.ResponseModelData
import com.example.myapplication.NetworkLayer.Webservice
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {
    companion object {
        const val ERROR_CODE = 400   //this for Wrong Password
    }

    fun login(email: String, password: String, type: Int): MutableLiveData<ResponseModelData> {
        val userData = MutableLiveData<ResponseModelData>()
        val body = LoginRequestModel(email.trim(), password, type.toString())
        Webservice.getInstance().api.login(body).enqueue(object : Callback<ResponseModelData> {
            override fun onResponse(
                call: Call<ResponseModelData>,
                response: Response<ResponseModelData>
            ) {
                if (response.isSuccessful) {
                    userData.value = response.body()
                } else {
                    if (response.code() == ERROR_CODE) {
                        var jObjError: JSONObject? = null
                        try {
                            jObjError = JSONObject(response.errorBody()?.string())
                        } catch (e: Exception) {
                            e.message
                        }
                        val dummyResponse =
                            ResponseModelData("", "", "", "", jObjError!!["data"].toString())
                        userData.value = dummyResponse
                    } else {
                        val dummyResponse =
                            ResponseModelData("", "", "", "", response.message())
                        userData.value = dummyResponse
                    }
                }
            }

            override fun onFailure(call: Call<ResponseModelData>, t: Throwable) {
                userData.value = null
            }
        })

        return userData

    }


}



