package com.example.myapplication.VotingFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.PollsAdapter
import com.example.myapplication.Adapters.VotingOptionsAdapter
import com.example.myapplication.Models.PollData
import com.example.myapplication.R
import kotlinx.android.synthetic.main.voting_fragment.*


class VotingFragment : Fragment() {
    private lateinit var root: View
    private lateinit var votingViewModel: VotingViewModel
    private var pollId: Int = 0
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<PollData>()
    private lateinit var pollAdapter: VotingOptionsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.voting_fragment, container, false)
        votingViewModel = ViewModelProviders.of(this).get(VotingViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        pollId = arguments?.getInt("POLL_ID")!!
        initRecyclerView()

    }

    private fun setClickListeners() {

        recyclerView = root.findViewById(R.id.votingRecycler)!!

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val list = listOf("hi", "huda", "hena", "ana", "skdkdks", "dsdsa", "dasddsa", "dsad")
        pollAdapter = VotingOptionsAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pollAdapter
        pollAdapter.setOnItemListener(object : VotingOptionsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {

            }

        })
    }


}