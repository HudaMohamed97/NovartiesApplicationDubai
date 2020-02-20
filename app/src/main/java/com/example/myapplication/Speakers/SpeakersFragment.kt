package com.example.myapplication.Speakers

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.SpeakerAdapter
import com.example.myapplication.Models.EventModels.Speakers
import com.example.myapplication.R
import kotlinx.android.synthetic.main.speakers_fragment.*


class SpeakersFragment : Fragment() {
    private var root: View? = null
    private lateinit var speakersViewModel: SpeakersViewModel
    private val list = arrayListOf<Speakers>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var speakerAdapter: SpeakerAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        speakersViewModel = ViewModelProviders.of(this).get(SpeakersViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.speakers_fragment, container, false)
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
        speakerProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            speakersViewModel.getSpeakers(accessToken)
        }
        speakersViewModel.getData().observe(this, Observer {
            speakerProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)
                }
                speakerAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        speakerAdapter = SpeakerAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = speakerAdapter
        speakerAdapter.setOnItemListener(object : SpeakerAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("Speaker", list[position])
                findNavController().navigate(R.id.action_clickSpeaker_toSpeakerProfile, bundle)
            }

        })
    }


    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.speakerRecycler)!!
    }


}