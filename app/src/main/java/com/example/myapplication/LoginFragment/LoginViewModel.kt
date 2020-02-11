package com.example.myapplication.LoginFragment

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.ResponseModelData


class LoginViewModel : ViewModel() {
    private var repositoryHelper: LoginRepository = LoginRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>
    private lateinit var shared: SharedPreferences

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    fun isLoading(): LiveData<Boolean> {
        if (!::isLoadingLiveData.isInitialized) {
            isLoadingLiveData = MutableLiveData()
            isLoadingLiveData.value = true
        }
        return isLoadingLiveData
    }


    fun validateLoginInfo(
        emailEt: String,
        passwordEt: String
    ): Boolean {
        val isEmailValid = Validation.validateEmail(emailEt)
        val isPasswordValid = Validation.validate(passwordEt)
        return !(!isEmailValid || !isPasswordValid)
    }


    fun login(emailEt: String, passwordEt: String, type: Int) {
        // isLoading.value = true
        mutableLiveData = repositoryHelper.login(emailEt, passwordEt, type)

    }

    fun getData(): MutableLiveData<ResponseModelData> {
        return mutableLiveData


    }


}







