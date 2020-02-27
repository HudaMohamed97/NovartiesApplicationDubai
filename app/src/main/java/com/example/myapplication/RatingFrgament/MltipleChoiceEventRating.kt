package com.example.myapplication.RatingFrgament

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
import com.example.myapplication.Adapters.MultipleChoiceAdapterEvent
import com.example.myapplication.Models.OptionModel
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.mutiple_choice_rating_event.*

class MltipleChoiceEventRating(private val optionId: Int, private val option: List<OptionModel>) :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    private lateinit var eventRatingViewModel: EventRatingViewModel
    private var questionId: Int = 0
    private var rateId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<OptionModel>()
    private lateinit var eventAdapter: MultipleChoiceAdapterEvent
    private lateinit var loginPreferences: SharedPreferences
    private var selectedList = arrayListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.mutiple_choice_rating_event, container, false)
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
        recyclerView = root.findViewById(R.id.multi_eventRatingRecycler)!!

        multi_submit_rate_option.setOnClickListener {
            if (selectedList.size == 0) {
                Toast.makeText(activity, "Please Select Answer", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitAnswer()
            }

        }
    }

    private fun callSubmitAnswer() {
        multi_eventRatingProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        questionId = optionId
        if (accessToken != null) {
            // questionId = optionId
            eventRatingViewModel.submitMutipleRate(selectedList, questionId, accessToken)
        }
        eventRatingViewModel.getDataMutipleSubmitRate().observe(this, Observer {
            multi_eventRatingProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "error" && it.type == "error")
                    Toast.makeText(
                        activity,
                        "You Already Submit this Vote Before, Thanks",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        eventAdapter = MultipleChoiceAdapterEvent(option)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = eventAdapter
        eventAdapter.setOnItemListener(object : MultipleChoiceAdapterEvent.OnItemClickListener {
            override fun onItemClicked(list: ArrayList<Int>) {
                selectedList = list
            }
        })
    }


}