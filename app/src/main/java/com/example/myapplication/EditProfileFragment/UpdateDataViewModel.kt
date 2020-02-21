package com.example.myapplication.EditProfileFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.updateDataModel
import okhttp3.ResponseBody

class UpdateDataViewModel : ViewModel() {
    private var repositoryHelper: UpdateDataRepository = UpdateDataRepository()
    private lateinit var mutableLiveData: MutableLiveData<updateDataModel>
    private lateinit var passwordMutableLiveData: MutableLiveData<ResponseBody>


    fun updateData(email: String, name: String, accessToken: String) {
        mutableLiveData = repositoryHelper.updateData(email, name, accessToken)

    }

    fun updatePassword(passwoed: String, accessToken: String) {
        passwordMutableLiveData = repositoryHelper.updatePassword(passwoed, accessToken)

    }

    fun getData(): MutableLiveData<updateDataModel> {
        return mutableLiveData
    }

    fun getPasswordData(): MutableLiveData<ResponseBody> {
        return passwordMutableLiveData
    }

}
