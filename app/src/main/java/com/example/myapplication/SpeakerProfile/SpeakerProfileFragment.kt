package com.example.myapplication.SpeakerProfile

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.LoginFragment.AgendaViewModel
import com.example.myapplication.LoginFragment.EventViewModel
import com.example.myapplication.Models.EventModels.Speakers
import com.example.myapplication.R
import kotlinx.android.synthetic.main.speaker_profile.*


class SpeakerProfileFragment : Fragment() {
    private lateinit var root: View
    private lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.speaker_profile, container, false)
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        val speaker = arguments?.getParcelable<Speakers>("Speaker")!!
        speaker_name.text = speaker.name
        speaker_Bio.text = speaker.bio


    }

    private fun setClickListeners() {


    }


}