package com.example.myapplication.EventFragment

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.LoginFragment.EventViewModel
import com.example.myapplication.R


class EventFragment : Fragment() {
    private lateinit var root: View
    private lateinit var eventViewModel: EventViewModel
    private var dialog: ProgressDialog? = null
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.events_fragment, container, false)
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        callGetEvents()
    }

    private fun callGetEvents() {
        showLoader()
        val accessToken = loginPreferences.getString("accessToken", "")
        val type = loginPreferences.getInt("type", 1)
        accessToken?.let { eventViewModel.getEvents(type, it) }
        eventViewModel.getData().observe(this, Observer {
            hideLoader()
            if (it != null) {
                Toast.makeText(activity, "success$it", Toast.LENGTH_SHORT).show()
                Log.i("hhh", "it.data[0].name$it")
            } else {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
            }
        })


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
        val mainLayout = root.findViewById(R.id.mainLayout) as View
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