package com.imagin.myapplication.SessionsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.Models.SessionsResponse
import com.imagin.myapplication.Models.SubmitModel

class SessionViewModel : ViewModel() {
    private var repositoryHelper: SessionRepository = SessionRepository()
    private lateinit var mutableLiveData: MutableLiveData<SessionsResponse>
    private lateinit var attendMutableLiveData: MutableLiveData<SubmitModel>

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

    fun submit(): MutableLiveData<SubmitModel> {
        return attendMutableLiveData
    }
}