package com.example.myapplication.RatingFrgament

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Models.Sessions
import com.example.myapplication.R
import kotlinx.android.synthetic.main.rating_fragment.*

class RatingFragment : Fragment() {
    private var sessionId: Int = -1
    private lateinit var root: View
    private var rate = -1
    private lateinit var ratingFragmentViewModel: RatingFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.rating_fragment, container, false)
        ratingFragmentViewModel =
            ViewModelProviders.of(this).get(RatingFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = arguments?.getParcelable<Sessions>("SESSION")!!
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        sessionId = session.id
        sessionName.text = session.title
        session_descriptoion.text = session.location
        val time = session.time_from + " " + session.time_to
        sessionDate.text = time
        setClickListeners()
    }

    private fun setClickListeners() {

        val comment = comment_layout.text.toString()
        submitRateButton.setOnClickListener {
            hideKeyboard()
            rate = ratingBar.rating.toInt()
            if (rate == -1 || rate == 0) {
                Toast.makeText(activity, "Please Rate Session", Toast.LENGTH_SHORT).show()
            } else {
                callSubmitRate(rate, comment)
            }
        }
        val logOutButton = root.findViewById(R.id.logOutButton) as ImageView
        val backButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            /* val preferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
             val editor = preferences.edit()
             editor.clear()
             editor.apply()*/
            activity!!.finish()
        }
        backButton.setOnClickListener {
            hideKeyboard()
            findNavController().navigateUp()
        }
    }


    private fun callSubmitRate(rate: Int, comment: String) {
        ratingProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            ratingFragmentViewModel.submitRate(sessionId, rate, comment, accessToken)
        }
        ratingFragmentViewModel.getData().observe(this, Observer {
            ratingProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "error" && it.type == "error")
                    Toast.makeText(
                        activity,
                        "You Already Submit this Vote Before",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
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