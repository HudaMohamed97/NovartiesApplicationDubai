package com.imagin.myapplication.NotificatonFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.NotificationModelResponse

class NotificationViewModel : ViewModel() {

    private var repositoryHelper: NotificationReporsitory = NotificationReporsitory()
    private lateinit var mutableLiveData: MutableLiveData<NotificationModelResponse>

    fun getData(): MutableLiveData<NotificationModelResponse> {
        return mutableLiveData
    }


    fun getNotification(pageId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getNotification(pageId, accessToken)
    }


}
