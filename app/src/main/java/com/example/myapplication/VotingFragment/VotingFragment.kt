package com.example.myapplication.VotingFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R


class VotingFragment : Fragment() {
    private lateinit var root: View
    private lateinit var votingViewModel: VotingViewModel


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
    }

    private fun setClickListeners() {


    }


}