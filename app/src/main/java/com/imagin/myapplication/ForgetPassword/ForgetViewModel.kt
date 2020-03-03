package com.imagin.myapplication.ForgetPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.SubmitModel

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