package com.imagin.myapplication.VotingFragment

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
import com.imagin.myapplication.Adapters.PollsAdapter
import com.imagin.myapplication.Models.PollData
import com.imagin.myapplication.R
import kotlinx.android.synthetic.main.polls_fragment.*


class PollFragment : Fragment() {
    private var root: View? = null
    private lateinit var pollViewModel: PollViewModel
    private val list = arrayListOf<PollData>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var pollAdapter: PollsAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pollViewModel = ViewModelProviders.of(this).get(PollViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.polls_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callSpeakersRequest()

    }

    private fun callSpeakersRequest() {
        pollsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            pollViewModel.getPolls(accessToken)
        }
        pollViewModel.getData().observe(this, Observer {
            pollsProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)
                }
                pollAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        pollAdapter = PollsAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pollAdapter
        pollAdapter.setOnItemListener(object : PollsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putInt("POLL_ID", list[position].id)
                findNavController().navigate(R.id.action_clickPoll_toPollDetails, bundle)
            }

        })
    }


    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.pollsRescycler)!!
        val logOutButton = root?.findViewById(R.id.logOutButton) as ImageView
        val backButton = root?.findViewById(R.id.backButton) as ImageView
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


}