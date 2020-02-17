package com.example.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.ResponseModelData


class LoginViewModel : ViewModel() {
    private var repositoryHelper: LoginRepository = LoginRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>

    val isLoadingLiveData = MutableLiveData<Boolean>().apply { value = false }


    fun validateLoginInfo(
        emailEt: String,
        passwordEt: String
    ): Boolean {
        val isEmailValid = Validation.validateEmail(emailEt)
        val isPasswordValid = Validation.validate(passwordEt)
        return !(!isEmailValid || !isPasswordValid)
    }


    fun login(emailEt: String, passwordEt: String, type: Int) {
        mutableLiveData = repositoryHelper.login(emailEt, passwordEt, type)

    }

    fun getData(): MutableLiveData<ResponseModelData> {
        return mutableLiveData
    }

}







