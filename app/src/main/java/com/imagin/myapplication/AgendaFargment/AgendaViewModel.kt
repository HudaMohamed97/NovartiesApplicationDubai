package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imagin.myapplication.AgendaFargment.AgendaRepository
import com.imagin.myapplication.Models.AgendaModelResponse


class AgendaViewModel : ViewModel() {
    private var agendaRepository: AgendaRepository =
        AgendaRepository()
    private lateinit var mutableLiveData: MutableLiveData<AgendaModelResponse>


    fun getData(): MutableLiveData<AgendaModelResponse> {
        return mutableLiveData
    }

    fun getAgendaData(day: Int, accessToken: String) {
        mutableLiveData = agendaRepository.getAgenda(day, accessToken)
    }


}







