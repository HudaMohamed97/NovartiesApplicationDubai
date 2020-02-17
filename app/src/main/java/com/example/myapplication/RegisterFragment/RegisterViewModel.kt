package com.example.myapplication.LoginFragment

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.RegisterRequestModel
import com.example.myapplication.Models.ResponseModelData


class RegisterViewModel : ViewModel() {
    private var repositoryHelper: RegisterRepository = RegisterRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>
    private lateinit var shared: SharedPreferences
    val isLoading = MutableLiveData<Boolean>()


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







