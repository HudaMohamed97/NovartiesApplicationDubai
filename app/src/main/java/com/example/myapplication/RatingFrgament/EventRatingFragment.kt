package com.example.myapplication.RatingFrgament

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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.QuestionsAdapter
import com.example.myapplication.Models.QuestionModel
import com.example.myapplication.R
import com.example.myapplication.RatingFrgament.EventRatingViewModel
import kotlinx.android.synthetic.main.notification_fragment.logOutButton
import kotlinx.android.synthetic.main.qustion_fragment.*
import androidx.lifecycle.Observer
import com.example.myapplication.Adapters.EventAdapterRating
import com.example.myapplication.HomeFragment.DeleteBottomSheet
import com.example.myapplication.HomeFragment.EventRatingBottomSheet
import com.example.myapplication.Models.AgendaRatingData


class EventRatingFragment : Fragment() {
    private var root: View? = null
    private lateinit var eventRatingViewModel: EventRatingViewModel
    private val list = arrayListOf<AgendaRatingData>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventRatingBottomSheet: EventRatingBottomSheet
    private lateinit var ratingProgressBar: ProgressBar
    private lateinit var questionsAdapter: EventAdapterRating
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventRatingViewModel = ViewModelProviders.of(this).get(EventRatingViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.event_rating, container, false)
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

        ratingProgressBar.visibility = View.VISIBLE

        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            eventRatingViewModel.getSessionData(accessToken)
        }
        eventRatingViewModel.getData().observe(this, Observer {

            ratingProgressBar.visibility = View.GONE

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
        questionsAdapter = EventAdapterRating(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = questionsAdapter
        questionsAdapter.setOnItemListener(object : EventAdapterRating.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                eventRatingBottomSheet = EventRatingBottomSheet(list[position].id)
                fragmentManager?.let { it ->
                    eventRatingBottomSheet.show(
                        it,
                        "eventRatingBottomSheet"
                    )
                }

            }
        })

    }

    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.eventRatingRecycler)!!
        ratingProgressBar = root?.findViewById(R.id.eventRating)!!
        val backButton = root?.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}