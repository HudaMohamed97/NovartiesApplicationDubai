package com.example.myapplication.LoginFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.ResponseModelData
import com.example.myapplication.Models.RegisterRequestModel
import com.example.myapplication.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {
    fun register(registerRequestModel: RegisterRequestModel): MutableLiveData<ResponseModelData> {
        val userData = MutableLiveData<ResponseModelData>()
        Webservice.getInstance().api.register(registerRequestModel)
            .enqueue(object : Callback<ResponseModelData> {
                override fun onResponse(
                    call: Call<ResponseModelData>,
                    response: Response<ResponseModelData>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        Log.i(
                            "hhhhhh",
                            "on failuer from sucess   " + response.message() + response.body()
                        )
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ResponseModelData>, t: Throwable) {
                    userData.value = null
                    Log.i("hhhhhh", "on fail in Login")
                    Log.i("hhhhhh", "on fail" + t.message)

                }
            })

        return userData

    }


}



