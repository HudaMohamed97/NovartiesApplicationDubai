package com.example.myapplication.HomeFragment

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var root: View
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }

    private fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val gridLayout = root.findViewById(R.id.mainGrid) as View
        locationCard.setOnClickListener {
            val uri = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?q=loc:%f,%f",
                28.43242324,
                77.8977673
            )
            val intent = Intent(ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
        agendaCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_AgendaFragment)
        }

        myPostsCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_PostsFragment)
        }
        votingCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_PollFragment)
        }
        speakers_card.setOnClickListener {

            findNavController().navigate(R.id.action_HomeFragment_to_SpeakersFragment)
        }
        ratingCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_RatingFragment)
        }
        sessionsCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_SessionFragment)
        }

        logOutButton.setOnClickListener {
            val preferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            activity!!.finish()
        }

        mainLayout.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}


