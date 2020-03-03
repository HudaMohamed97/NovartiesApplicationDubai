package com.imagin.myapplication.EventFragment

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.imagin.myapplication.LoginFragment.EventViewModel
import com.imagin.myapplication.R


class EventFragment : Fragment() {
    private lateinit var root: View
    private lateinit var eventViewModel: EventViewModel
    private var dialog: ProgressDialog? = null


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