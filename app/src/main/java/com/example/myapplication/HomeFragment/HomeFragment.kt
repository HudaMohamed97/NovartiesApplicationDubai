package com.example.myapplication.HomeFragment

import android.app.ProgressDialog
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Models.EventModels.Speakers
import com.example.myapplication.R
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private var root: View? = null
    private lateinit var homeViewModel: HomeViewModel
    private var dialog: ProgressDialog? = null
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var speakers: ArrayList<Speakers>
    private var firstCreated: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (root != null) {
            homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
            firstCreated = false
            root

        } else {
            root = inflater.inflate(R.layout.home_fragment, container, false)
            homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
            firstCreated = true
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        if (root != null && firstCreated) {
            callGetEvents()
        }
    }


    private fun hideLoader() {
        dialog?.dismiss()

    }

    private fun showLoader() {
        dialog = ProgressDialog(activity)
        dialog?.setMessage("Please, Wait")
        dialog?.setCancelable(false)
        dialog?.show()
    }


    private fun setClickListeners() {
        val mainLayout = root?.findViewById(R.id.mainLayout) as View
        val gridLayout = root?.findViewById(R.id.mainGrid) as View
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
            val bundle = Bundle()
            bundle.putParcelableArrayList("Speakers", speakers)
            findNavController().navigate(R.id.action_HomeFragment_to_AgendaFragment, bundle)
        }

        myPostsCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_PostsFragment)
        }
        votingCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_VotingFragment)
        }
        speakers_card.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("Speakers", speakers)
            findNavController().navigate(R.id.action_HomeFragment_to_SpeakersFragment, bundle)
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

    private fun callGetEvents() {
        showLoader()
        val accessToken = loginPreferences.getString("accessToken", "")
        val type = loginPreferences.getInt("type", 1)
        accessToken?.let { homeViewModel.getEvents(type, it) }
        homeViewModel.getData().observe(this, Observer {
            if (it != null) {
                callSingleEvent(it.data[0].id)
            } else {
                Toast.makeText(activity, " Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callSingleEvent(eventId: Int) {
        val accessToken = loginPreferences.getString("accessToken", "")
        val type = loginPreferences.getInt("type", 1)
        accessToken?.let { homeViewModel.getSingleEvent(eventId, type, it) }
        homeViewModel.getSingleEventData().observe(this, Observer {
            hideLoader()
            if (it != null) {
                speakers = it.data.speakers as ArrayList<Speakers>
                Toast.makeText(activity, " Succss", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, " Network Error", Toast.LENGTH_SHORT).show()
            }
        })


    }


}