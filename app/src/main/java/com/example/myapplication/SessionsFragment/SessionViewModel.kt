package com.example.myapplication.SessionsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SessionsResponse
import com.example.myapplication.Models.submitModel

class SessionViewModel : ViewModel() {
    private var repositoryHelper: SessionRepository = SessionRepository()
    private lateinit var mutableLiveData: MutableLiveData<SessionsResponse>
    private lateinit var attendMutableLiveData: MutableLiveData<submitModel>

    fun getSessions(accessToken: String) {
        mutableLiveData = repositoryHelper.getSessions(accessToken)

    }

    fun getData(): MutableLiveData<SessionsResponse> {
        return mutableLiveData
    }

    fun submitAttendance(
        state: Int,
        sessionId: Int, accessToken: String
    ) {
        attendMutableLiveData = repositoryHelper.submitAttendance(state, sessionId, accessToken)

    }

    fun submit(): MutableLiveData<submitModel> {
        return attendMutableLiveData
    }
}