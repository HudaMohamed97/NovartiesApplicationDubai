package com.example.myapplication.QuestionsFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.QuestionsAdapter
import com.example.myapplication.Models.QuestionModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.notification_fragment.logOutButton
import kotlinx.android.synthetic.main.qustion_fragment.*

class QuestionsFragment : Fragment() {
    private var root: View? = null
    private lateinit var questionsViewModel: QuestionsViewModel
    private val list = arrayListOf<QuestionModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationProgressBar: ProgressBar
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.qustion_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callQuestionsRequest(false)
    }

    private fun callQuestionsRequest(fromLoadMore: Boolean) {
        if (fromLoadMore) {
            QuestionMoreLoadProgressBar.visibility = View.VISIBLE
        } else {
            QuestionProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            questionsViewModel.getQuestions(accessToken)
        }
        questionsViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                QuestionMoreLoadProgressBar.visibility = View.GONE
            } else {
                QuestionProgressBar.visibility = View.GONE
            }
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)

                }
                questionsAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        questionsAdapter = QuestionsAdapter(list)
        questionsRecycler.layoutManager = layoutManager
        questionsRecycler.adapter = questionsAdapter
        questionsAdapter.setOnItemListener(object : QuestionsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                if (position == 0) {
                    val bundle = Bundle()
                    bundle.putInt("question_id", list[position].id)
                    findNavController().navigate(R.id.action_clickSession_toQuestionDetails, bundle)
                } else if (position == 1) {
                    val bundle = Bundle()
                    bundle.putInt("question_id", list[position].id)
                    findNavController().navigate(
                        R.id.action_clickSession_toSecondQuestionDetails,
                        bundle
                    )

                }
            }
        })

    }

    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.questionsRecycler)!!
        notificationProgressBar = root?.findViewById(R.id.QuestionProgressBar)!!
        val backButton = root?.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}