package com.example.myapplication.EditProfileFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.updateDataModel
import com.example.myapplication.NetworkLayer.Webservice
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MultipartBody
import java.io.File


class UpdateDataRepository {
    fun updateData(
        file: String,
        email: String,
        name: String,
        accessToken: String
    ): MutableLiveData<updateDataModel> {
        val singleEventData = MutableLiveData<updateDataModel>()

        val email = RequestBody.create(MediaType.parse("multipart/form-data"), email)
        val name = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val myfile = File(file)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myfile)
        val image = MultipartBody.Part.createFormData("photo", myfile?.name, requestFile)

        Webservice.getInstance().api.updateAccount(email, name, image, accessToken)
            .enqueue(object : Callback<updateDataModel> {
                override fun onResponse(
                    call: Call<updateDataModel>,
                    response: Response<updateDataModel>
                ) {
                    if (response.isSuccessful) {
                        response.raw()
                        singleEventData.value = response.body()
                    } else {
                        singleEventData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<updateDataModel>, t: Throwable) {
                    singleEventData.value = null
                }
            })
        return singleEventData
    }

    fun updatePassword(passwoed: String, accessToken: String): MutableLiveData<ResponseBody> {
        val singleEventData = MutableLiveData<ResponseBody>()

        /* Webservice.getInstance().api.updateAccount(passwoed, accessToken)
             .enqueue(object : Callback<updateDataModel> {
                 override fun onResponse(
                     call: Call<updateDataModel>,
                     response: Response<updateDataModel>
                 ) {
                     if (response.isSuccessful) {
                         response.raw()
                         singleEventData.value = response.body()
                     } else {
                         singleEventData.value = response.body()
                     }
                 }

                 override fun onFailure(call: Call<updateDataModel>, t: Throwable) {
                     singleEventData.value = null
                 }
             })*/
        return singleEventData
    }

}
