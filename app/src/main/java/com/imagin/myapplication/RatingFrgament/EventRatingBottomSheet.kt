package com.imagin.myapplication.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Adapters.EventRatingAdapter
import com.imagin.myapplication.R
import com.imagin.myapplication.RatingFrgament.EventRatingViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.event_rating_sheet.*
import kotlinx.android.synthetic.main.rating_bottom_sheet.submit_rate_option

class EventRatingBottomSheet(private var optionId: Int) : BottomSheetDialogFragment() {
    private lateinit var root: View
    private lateinit var eventRatingViewModel: EventRatingViewModel
    private var questionId: Int = 0
    private var rateId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<Int>()
    private lateinit var eventAdapter: EventRatingAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.event_rating_sheet, container, false)
        eventRatingViewModel = ViewModelProviders.of(this).get(EventRatingViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()

    }

    private fun setClickListeners() {
        recyclerView = root.findViewById(R.id.eventRatingRecycler)!!

        submit_rate_option.setOnClickListener {
            if (rateId == -1) {
                Toast.makeText(activity, "Please Select Answer", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitAnswer()
            }

        }
    }

    private fun callSubmitAnswer() {
        eventRatingProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")

        if (accessToken != null) {
            questionId = optionId
            eventRatingViewModel.submitRate(questionId, rateId, accessToken)
        }
        eventRatingViewModel.getDataSubmitRate().observe(this, Observer {
            eventRatingProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "error" && it.type == "error")
                    Toast.makeText(
                        activity,
                        "You Already Submit this Vote Before",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        for (i in 0..10) {
            list.add(i)
        }
        eventAdapter = EventRatingAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = eventAdapter
        eventAdapter.setOnItemListener(object : EventRatingAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                rateId = list[position]
            }
        })
    }


}