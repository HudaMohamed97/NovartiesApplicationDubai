package com.example.myapplication.LoginFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.NetworkLayer.Webservice
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    fun login(email: String, password: String): MutableLiveData<ResponseBody> {
        val userData = MutableLiveData<ResponseBody>()
        val body = mapOf(
            "email" to email.trim(),
            "password" to password
        )
        Webservice.getInstance().api.login(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                userData.value = null
                Log.i("hhhhhh", "on fail")
                Log.i("hhhhhh", "on fail" + t.message)

            }
        })

        return userData

    }


}