package com.example.myapplication.SpeakerProfile

import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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
        speaker_Bio.movementMethod = ScrollingMovementMethod()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            speaker_Bio.text = Html.fromHtml(speaker.bio, Html.FROM_HTML_MODE_LEGACY)
        } else {
            speaker_Bio.text = Html.fromHtml(speaker.bio)
        }

        Glide.with(context!!).load(speaker.photo).centerCrop()
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile).into(imgProfile)
        val logOutButton = root.findViewById(R.id.logOutButton) as ImageView
        val backButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setClickListeners() {


    }


}