package com.imagin.myapplication.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.imagin.myapplication.Models.Account
import com.imagin.myapplication.R
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {
    private lateinit var root: View
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var account: Account
    private lateinit var customBottomSheet: LocationBottomSheet


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
        account = arguments?.getParcelable("Account")!!
        user_name.text = account.name
        Glide.with(context!!).load(account.photo).centerCrop()
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile).into(imgProfile)
    }

    private fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val gridLayout = root.findViewById(R.id.mainGrid) as View
        locationCard.setOnClickListener {
            callLocationData()
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
            findNavController().navigate(R.id.action_Home_To_EventRating)
        }
        sessionsCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_QuestionFragment)
        }
        profile_card.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("ACCOUNT", account)
            findNavController().navigate(R.id.action_HomeFragment_to_UpdateFragment, bundle)
        }

        notification.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_NotificationFragment)
        }

        mainLayout.setOnClickListener {
            hideKeyboard()
        }

        question_button.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_SessionFragment)
        }


    }

    private fun callLocationData() {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            homeViewModel.getLocation(accessToken)
        }
        homeViewModel.getData().observe(this, Observer {
            if (it != null) {
                customBottomSheet = LocationBottomSheet(it)
                fragmentManager?.let { it ->
                    customBottomSheet.show(
                        it,
                        "Location"
                    )
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }

        })


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


