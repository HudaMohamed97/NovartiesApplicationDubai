package com.example.myapplication.ForgetPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel

class ForgetViewModel : ViewModel() {
    private var repositoryHelper: ForgetRepository = ForgetRepository()
    private lateinit var addMutableLiveData: MutableLiveData<SubmitModel>


    fun resetPassword(
        email: String
    ) {
        addMutableLiveData = repositoryHelper.resetEmail(email)

    }

    fun getData(): MutableLiveData<SubmitModel> {
        return addMutableLiveData
    }
}