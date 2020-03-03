package com.imagin.myapplication.RatingFrgament

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
import com.imagin.myapplication.Adapters.SessionsRatingAdapter
import com.imagin.myapplication.LoginFragment.AgendaViewModel
import com.imagin.myapplication.Models.Sessions
import com.imagin.myapplication.R
import kotlinx.android.synthetic.main.saeesio_rating_fragment.*

class RatingSessionFragment : Fragment() {
    private var root: View? = null
    private lateinit var agendaViewModel: AgendaViewModel
    private val list = arrayListOf<Sessions>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var ratingSpinner: Spinner
    private lateinit var sessionRatingAdapter: SessionsRatingAdapter
    private lateinit var loginPreferences: SharedPreferences
    private var fromBack = false
    private var numberOfDays = 1
    var enteredBefore = false
    var listDays = arrayListOf<Int>()
    val listofDays = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)
        return if (root != null) {
            fromBack = true
            root
        } else {
            fromBack = false
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
        if (!fromBack) {
            callGetAgendaData(1)
        }
        ratingspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val selectedDay = listDays[position]
                    callGetAgendaData(selectedDay)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }

    }

    private fun initializeSpinner(numberOfDays: Int) {
        enteredBefore = true
        for (i in 0..numberOfDays) {
            listDays.add(i)
        }
        for (day in listDays) {
            if (day == 0) {
                listofDays.add("Days ")
            } else {
                listofDays.add("Day " + day)
            }
        }
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.spinner_item,
                    listofDays
                )
            }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            ratingspinner.adapter = arrayAdapter
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
                if (it.day.id != -1) {
                    numberOfDays = it.num_of_days
                    numberOfDays = it.num_of_days
                    if (!enteredBefore) {
                        initializeSpinner(numberOfDays)
                    }
                    list.clear()
                    for (session in it.day.sessions) {
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