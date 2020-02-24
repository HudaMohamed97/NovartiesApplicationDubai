package com.example.myapplication.QuestionsFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.QuestionDetailsAdapter
import com.example.myapplication.Models.EventModels.QuestionModelOption
import com.example.myapplication.R
import kotlinx.android.synthetic.main.question_details_fragment.*
import kotlinx.android.synthetic.main.voting_fragment.*

class QuestionDetailsFragment : Fragment() {
    private lateinit var root: View
    private lateinit var questionViewModel: QuestionsViewModel
    private var questionId: Int = 0
    private var optionId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<QuestionModelOption>()
    private lateinit var quetionAdapter: QuestionDetailsAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.question_details_fragment, container, false)
        questionViewModel = ViewModelProviders.of(this).get(QuestionsViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        questionId = arguments?.getInt("question_id")!!
        initRecyclerView()
        callSingelQuestion()
    }

    private fun callSingelQuestion() {
        QuestionDetailsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            questionViewModel.getSingelQuestions(questionId, accessToken)
        }
        questionViewModel.getSingelQuestionData().observe(this, Observer {
            QuestionDetailsProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                questionTitle.text = it.data.title
                for (data in it.data.options) {
                    list.add(data)
                }
                quetionAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun setClickListeners() {
        recyclerView = root.findViewById(R.id.answersRecycler)!!

        submitAnswer.setOnClickListener {
            if (optionId == -1) {
                Toast.makeText(activity, "Please Select Answer", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitAnswer()
            }

        }
        val logOutButton = root.findViewById(R.id.logOutButton) as ImageView
        val backButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun callSubmitAnswer() {
        QuestionDetailsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            questionViewModel.submitQuestions(questionId, optionId, accessToken)
        }
        questionViewModel.getSubmitData().observe(this, Observer {
            QuestionDetailsProgressBar.visibility = View.GONE
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
        quetionAdapter = QuestionDetailsAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = quetionAdapter
        quetionAdapter.setOnItemListener(object : QuestionDetailsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                optionId = list[position].id
            }
        })
    }
}
