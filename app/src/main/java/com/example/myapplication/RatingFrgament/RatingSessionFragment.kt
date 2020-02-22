package com.example.myapplication.RatingFrgament

import android.content.Context
import android.content.SharedPreferences
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
import com.example.myapplication.Adapters.SessionsRatingAdapter
import com.example.myapplication.LoginFragment.AgendaViewModel
import com.example.myapplication.Models.Sessions
import com.example.myapplication.R
import kotlinx.android.synthetic.main.saeesio_rating_fragment.*

class RatingSessionFragment : Fragment() {
    private var root: View? = null
    private lateinit var agendaViewModel: AgendaViewModel
    private val list = arrayListOf<Sessions>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var ratingSpinner: Spinner
    private lateinit var sessionRatingAdapter: SessionsRatingAdapter
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
            root = inflater.inflate(R.layout.saeesio_rating_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val logOutButton = root?.findViewById(R.id.logOutButton) as ImageView
        val backButton = root?.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        initRecyclerView()
        initializeSpinner()
        callGetAgendaData(1)
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
        ratingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position >= 0) {
                    val selectedDay = list[position]
                    callGetAgendaData(selectedDay)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            ratingSpinner.adapter = arrayAdapter
        }
    }

    private fun callGetAgendaData(day: Int) {
        ratingProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            agendaViewModel.getAgendaData(day, accessToken)
        }
        agendaViewModel.getData().observe(this, Observer {
            ratingProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.data.id != -1) {
                    list.clear()
                    for (session in it.data.sessions) {
                        list.add(session)
                    }
                    sessionRatingAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "NO agenda For This DAY", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView = root?.findViewById(R.id.ratingRescycler)!!
        ratingSpinner = root?.findViewById(R.id.ratingspinner)!!
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        sessionRatingAdapter = SessionsRatingAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = sessionRatingAdapter
        sessionRatingAdapter.setOnItemListener(object : SessionsRatingAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("SESSION", list[position])
                findNavController().navigate(R.id.action_clickSession_toRatingDetails, bundle)
            }

        })
    }
}