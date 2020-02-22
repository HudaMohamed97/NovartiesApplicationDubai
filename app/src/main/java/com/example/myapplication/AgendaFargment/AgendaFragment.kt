package com.example.myapplication.AgendaFargment

import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.AgendaAdapter
import com.example.myapplication.LoginFragment.AgendaViewModel
import com.example.myapplication.Models.AdendaModel
import com.example.myapplication.Models.AgendaData
import com.example.myapplication.Models.EventModels.Speakers
import com.example.myapplication.Models.Sessions
import com.example.myapplication.R
import kotlinx.android.synthetic.main.agenda_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.speakers_fragment.*


class AgendaFragment : Fragment() {
    private var root: View? = null
    private lateinit var agendaViewModel: AgendaViewModel
    private val list = arrayListOf<Sessions>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var agendaAdapter: AgendaAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.agenda_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        initRecyclerView()
        initializeSpinner()
        callGetAgendaData(1)
        val logOutButton = root?.findViewById(R.id.logOutButton) as ImageView
        val backButton = root?.findViewById(R.id.backButton) as ImageView

        logOutButton.setOnClickListener {
            /* val preferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
             val editor = preferences.edit()
             editor.clear()
             editor.apply()*/
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun callGetAgendaData(day: Int) {
        agendaProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            agendaViewModel.getAgendaData(day, accessToken)
        }
        agendaViewModel.getData().observe(this, Observer {
            agendaProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.data.id != -1) {
                    list.clear()
                    for (session in it.data.sessions) {
                        list.add(session)
                    }
                    agendaAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "NO agenda For This DAY", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initializeSpinner() {
        val list = arrayListOf<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.spinner_item,
                    list
                )
            }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val selectedDay = list[position]
                callGetAgendaData(selectedDay)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinner.adapter = arrayAdapter
        }
    }

    private fun initRecyclerView() {
        recyclerView = root?.findViewById(R.id.agendaRecycler)!!
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        agendaAdapter = AgendaAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = agendaAdapter
        agendaAdapter.setOnItemListener(object : AgendaAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {

            }

        })
    }
}