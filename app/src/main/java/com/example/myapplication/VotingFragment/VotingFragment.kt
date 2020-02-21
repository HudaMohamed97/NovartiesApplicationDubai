package com.example.myapplication.VotingFragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.VotingOptionsAdapter
import com.example.myapplication.Models.OptionModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.voting_fragment.*


class VotingFragment : Fragment() {
    private lateinit var root: View
    private lateinit var votingViewModel: PollViewModel
    private var pollId: Int = 0
    private var optionId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<OptionModel>()
    private lateinit var pollAdapter: VotingOptionsAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.voting_fragment, container, false)
        votingViewModel = ViewModelProviders.of(this).get(PollViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        pollId = arguments?.getInt("POLL_ID")!!
        initRecyclerView()
        callSingePoll()

    }

    private fun callSingePoll() {
        OptionsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            votingViewModel.getSingePolls(pollId, accessToken)
        }
        votingViewModel.getSinglePollData().observe(this, Observer {
            OptionsProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                pollTitle.text = it.data.title
                for (data in it.data.options) {
                    list.add(data)
                }
                pollAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setClickListeners() {
        recyclerView = root.findViewById(R.id.votingRecycler)!!
        submitVote.setOnClickListener {
            if (optionId == -1) {
                Toast.makeText(activity, "Please Select Poll", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitVote()
            }

        }
        val logOutButton = root.findViewById(R.id.logOutButton) as ImageView
        val backButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            /* val preferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
             val editor = preferences.edit()
             editor.clear()
             editor.apply()*/
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun callSubmitVote() {
        OptionsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            votingViewModel.callSubmitOption(pollId, optionId, accessToken)
        }
        votingViewModel.getSubmittedStatues().observe(this, Observer {
            OptionsProgressBar.visibility = View.GONE
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
        pollAdapter = VotingOptionsAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pollAdapter
        pollAdapter.setOnItemListener(object : VotingOptionsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                optionId = list[position].id
            }
        })
    }


}