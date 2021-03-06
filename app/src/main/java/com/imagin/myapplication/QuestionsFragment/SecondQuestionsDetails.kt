package com.imagin.myapplication.QuestionsFragment


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
import com.imagin.myapplication.Adapters.SecondQuestionDetailsAdapter
import com.imagin.myapplication.HomeFragment.RatingBottomSheet
import com.imagin.myapplication.Models.EventModels.QuestionModelOption
import com.imagin.myapplication.R
import kotlinx.android.synthetic.main.question_details_fragment.QuestionDetailsProgressBar
import kotlinx.android.synthetic.main.second_question_details_fragment.*

class SecondQuestionsDetails : Fragment() {
    private lateinit var root: View
    private var questionId: Int = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionViewModel: QuestionsViewModel
    private val list = arrayListOf<QuestionModelOption>()
    private lateinit var quetionAdapter: SecondQuestionDetailsAdapter
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var customBottomSheet: RatingBottomSheet


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.second_question_details_fragment, container, false)
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
                secondQuestionTitle.text = it.data.title
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
        recyclerView = root.findViewById(R.id.secondAnswersRecycler)!!
        val logOutButton = root.findViewById(R.id.logOutButton) as ImageView
        val backButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        quetionAdapter = SecondQuestionDetailsAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = quetionAdapter
        quetionAdapter.setOnItemListener(object : SecondQuestionDetailsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {

                customBottomSheet = RatingBottomSheet(list[position].id)
                fragmentManager?.let { it ->
                    customBottomSheet.show(
                        it,
                        "Location"
                    )
                }
            }
        })
    }
}
