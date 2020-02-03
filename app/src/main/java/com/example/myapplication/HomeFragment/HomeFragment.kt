package com.example.myapplication.HomeFragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var root: View
    private lateinit var loginViewModel: HomeViewModel
    private var dialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false)
        loginViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        //listenToLoading()
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
        val gridLayout = root.findViewById(R.id.mainGrid) as View
        card1.setOnClickListener {
            val uri = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?q=loc:%f,%f",
                28.43242324,
                77.8977673
            )
            val intent = Intent(ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
            //  findNavController().navigate(R.id.action_HomeFragment_to_event)

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