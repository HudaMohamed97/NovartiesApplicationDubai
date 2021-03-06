package com.imagin.myapplication.EditProfileFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.Account
import com.imagin.myapplication.Models.updateDataModel

class UpdateDataViewModel : ViewModel() {
    private var repositoryHelper: UpdateDataRepository = UpdateDataRepository()
    private lateinit var mutableLiveData: MutableLiveData<updateDataModel>
    private lateinit var passwordMutableLiveData: MutableLiveData<Account>


    fun updateData(file: String, email: String, name: String, accessToken: String) {
        mutableLiveData = repositoryHelper.updateData(file, email, name, accessToken)

    }

    fun updatePassword(newPasswoed: String, oldPassword: String, accessToken: String) {
        passwordMutableLiveData =
            repositoryHelper.updatePassword(newPasswoed, oldPassword, accessToken)

    }

    fun getData(): MutableLiveData<updateDataModel> {
        return mutableLiveData
    }

    fun getPasswordData(): MutableLiveData<Account> {
        return passwordMutableLiveData
    }

}
