package com.imagin.myapplication.ForgetPassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.imagin.myapplication.R
import kotlinx.android.synthetic.main.forget_password.*

class ForgetPasswordFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: ForgetViewModel
    private lateinit var email: EditText
    private lateinit var emailText: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstancelState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.forget_password, container, false)
        registerViewModel = ViewModelProviders.of(this).get(ForgetViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = root.findViewById(R.id.reset_email)
        setListeners()
    }

    private fun setListeners() {
        reset_pass_button.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (emailText.isNotEmpty()) {
                callForgetRequest()
            } else {
                Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
            }

        }
        reset_back.setOnClickListener()
        {
            findNavController().navigateUp()
        }
    }

    private fun callForgetRequest() {
        resetProgressBar.visibility = View.VISIBLE
        registerViewModel.resetPassword(emailText)
        registerViewModel.getData().observe(this, Observer {
            resetProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "NoAccount" && it.type == "NoAccount")
                    Toast.makeText(
                        activity,
                        "No Account for this Email",
                        Toast.LENGTH_SHORT
                    ).show()
                else if (it.title == "NotValid" && it.type == "NotValid") {
                    Toast.makeText(
                        activity,
                        "Account must Be Valid Email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(activity, "Reset Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun checkErrorEnabled() {
        getUserInputData()
    }

    private fun getUserInputData() {
        emailText = email.text.toString()
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