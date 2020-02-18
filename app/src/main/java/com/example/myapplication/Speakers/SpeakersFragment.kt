package com.example.myapplication.Speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.AgendaAdapter
import com.example.myapplication.LoginFragment.AgendaViewModel
import com.example.myapplication.Models.AdendaModel
import com.example.myapplication.Models.EventModels.Speakers
import com.example.myapplication.R


class SpeakersFragment : Fragment() {
    private var root: View? = null
    private lateinit var agendaViewModel: AgendaViewModel
    private lateinit var list: ArrayList<Speakers>
    private lateinit var recyclerView: RecyclerView
    private lateinit var agendaAdapter: AgendaAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)
        return if (root != null) {
            root
        } else {
            root = inflater.inflate(R.layout.speakers_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = arguments?.getParcelableArrayList<Speakers>("Speakers")!!
        setClickListeners()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        val agendaList = ArrayList<AdendaModel>()
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        for (speaker in list) {
            agendaList.add(AdendaModel(speaker.name, "time", speaker.bio, "title"))
        }

        agendaAdapter = AgendaAdapter(agendaList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = agendaAdapter
        agendaAdapter.setOnItemListener(object : AgendaAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("Speaker", list[position])
                findNavController().navigate(R.id.action_clickSpeaker_toSpeakerProfile, bundle)

            }

        })
    }


    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.agendaRecycler)!!
    }


}