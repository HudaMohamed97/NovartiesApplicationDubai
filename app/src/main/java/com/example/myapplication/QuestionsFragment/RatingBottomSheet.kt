package com.example.myapplication.HomeFragment

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
import com.example.myapplication.Adapters.QuestionRatingAdapter
import com.example.myapplication.QuestionsFragment.QuestionsViewModel
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.rating_bottom_sheet.*

class RatingBottomSheet(private var optionId: Int) : BottomSheetDialogFragment() {
    private lateinit var root: View
    private lateinit var questionViewModel: QuestionsViewModel
    private var questionId: Int = 0
    private var rateId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<Int>()
    private lateinit var quetionAdapter: QuestionRatingAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.rating_bottom_sheet, container, false)
        questionViewModel = ViewModelProviders.of(this).get(QuestionsViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()

    }

    private fun setClickListeners() {
        recyclerView = root.findViewById(R.id.answersRatingRecycler)!!

        submit_rate_option.setOnClickListener {
            if (rateId == -1) {
                Toast.makeText(activity, "Please Select Answer", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitAnswer()
            }

        }
    }

    private fun callSubmitAnswer() {
        ratingDetailsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")

        if (accessToken != null) {
            questionId = optionId
            questionViewModel.submitRateOptionQuestions(questionId, rateId, accessToken)
        }
        questionViewModel.getSubmitDataRate().observe(this, Observer {
            ratingDetailsProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "error" && it.type == "error")
                    Toast.makeText(
                        activity,
                        "You Already Submit this Vote Before",
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
        list.add(0)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        quetionAdapter = QuestionRatingAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = quetionAdapter
        quetionAdapter.setOnItemListener(object : QuestionRatingAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                rateId = list[position]
            }
        })
    }


}