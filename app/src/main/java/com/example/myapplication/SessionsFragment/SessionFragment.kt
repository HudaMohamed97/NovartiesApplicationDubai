package com.example.myapplication.SessionsFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.SessionAdapter
import com.example.myapplication.Models.SessionsData
import com.example.myapplication.R
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.sessions_list.*
import kotlinx.android.synthetic.main.sessions_list.backButton


class SessionFragment : Fragment() {
    private var root: View? = null
    private lateinit var sessionViewModel: SessionViewModel
    private val list = arrayListOf<SessionsData>()
    private lateinit var recyclerView: RecyclerView
    private var sessionId = -1
    private var statuesFlag = 0
    private lateinit var sessionAdapter: SessionAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.sessions_list, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callGetSessions()

    }

    private fun callGetSessions() {
        SessionsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            sessionViewModel.getSessions(accessToken)
        }
        sessionViewModel.getData().observe(this, Observer {
            SessionsProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)
                }
                sessionAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        for (session in list) {
            list.add(session)
        }

        sessionAdapter = SessionAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = sessionAdapter
        sessionAdapter.setOnItemListener(object : SessionAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, statues: Int) {
                sessionId = list[position].id
                statuesFlag = statues
            }

        })
    }


    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.sessionRecycler)!!
        btn_submit.setOnClickListener {
            if (sessionId == -1) {
                Toast.makeText(activity, "Please Select Yes Or No", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitAttendance()
            }
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun callSubmitAttendance() {
        SessionsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            sessionViewModel.submitAttendance(statuesFlag, sessionId, accessToken)
        }
        sessionViewModel.submit().observe(this, Observer {
            SessionsProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Submitted Successfully" + statuesFlag, Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(activity, "You Already Submit this  Before", Toast.LENGTH_SHORT)
                    .show()
            }


        })
    }


}
