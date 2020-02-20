package com.example.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.AgendaFargment.AgendaRepository
import com.example.myapplication.Models.AgendaModelResponse


class AgendaViewModel : ViewModel() {
    private var agendaRepository: AgendaRepository =
        AgendaRepository()
    private lateinit var mutableLiveData: MutableLiveData<AgendaModelResponse>


    fun getData(): MutableLiveData<AgendaModelResponse> {
        return mutableLiveData
    }

    fun getAgendaData(accessToken: String) {
        mutableLiveData = agendaRepository.getAgenda(accessToken)
    }


}







