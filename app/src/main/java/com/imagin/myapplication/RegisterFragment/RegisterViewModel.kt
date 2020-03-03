package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.catapplication.utilies.Validation
import com.imagin.myapplication.Models.RegisterRequestModel
import com.imagin.myapplication.Models.ResponseModelData


class RegisterViewModel : ViewModel() {
    private var repositoryHelper: RegisterRepository = RegisterRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>


    fun validateDataInfo(
        emailEt: String,
        passwordEt: String
    ): Boolean {
        val isEmailValid = Validation.validateEmail(emailEt)
        val isPasswordValid = Validation.validate(passwordEt)
        return !(!isEmailValid || !isPasswordValid)
    }

    fun register(registerRequestModel: RegisterRequestModel) {
        mutableLiveData = repositoryHelper.register(registerRequestModel)

    }

    fun getData(): MutableLiveData<ResponseModelData> {
        return mutableLiveData
    }
}







