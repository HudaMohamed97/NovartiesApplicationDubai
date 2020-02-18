package com.example.myapplication.RegisterFragment

import android.app.ProgressDialog
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
import com.example.catapplication.utilies.Validation
import com.example.myapplication.LoginFragment.RegisterViewModel
import com.example.myapplication.Models.RegisterRequestModel
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var email: EditText
    private lateinit var passwordEt: EditText
    private lateinit var rePasswordEt: EditText
    private lateinit var phone: EditText
    private lateinit var name: EditText
    private lateinit var emailText: String
    private lateinit var passwordText: String
    private lateinit var RePasswordText: String
    private lateinit var phoneText: String
    private lateinit var nameText: String
    private lateinit var FromFragment: String
    private var validName = false
    private var validPhone = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.register_fragment, container, false)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FromFragment = arguments?.getString("fromFragment").toString()
        email = root.findViewById(R.id.input_email)
        passwordEt = root.findViewById(R.id.input_password)
        rePasswordEt = root.findViewById(R.id.input_RePassword)
        name = root.findViewById(R.id.input_Name)
        phone = root.findViewById(R.id.input_Phone)
        setListeners()
    }

    private fun setListeners() {
        register_login.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (registerViewModel.validateDataInfo(
                    emailText
                    , passwordText
                ) && validName && validPhone
            ) {
                callRegisterRequest()
            } else {
                Toast.makeText(activity, "Please fill all Data", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun callRegisterRequest() {
        progressBar.visibility = View.VISIBLE
        val requestModel = RegisterRequestModel(emailText, passwordText, nameText, phoneText)
        registerViewModel.register(requestModel)
        registerViewModel.getData().observe(this, Observer {
            progressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Register Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun checkErrorEnabled() {
        getUserInputData()
        checkNameInput()
        checkPhoneInput()
        checkEmailInput()
        checkPassInput()
    }

    private fun checkPassInput() {
        if (!Validation.validate(passwordText)) {
            Toast.makeText(activity, "empty password please fill it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkEmailInput() {
        if (!Validation.validate(emailText)) {
            Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
        } else {
            if (!Validation.validateEmail(emailText)) {
                Toast.makeText(
                    activity,
                    "Invalid Email Format Please enter valid mail",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkPhoneInput() {
        if (!Validation.validate(phoneText)) {
            Toast.makeText(activity, "empty phone please fill it", Toast.LENGTH_SHORT).show()
            validPhone = false
        } else {
            validPhone = if (!Validation.isValidPhone(phoneText)) {
                Toast.makeText(activity, "wrong phone format", Toast.LENGTH_SHORT).show()
                false
            } else {
                true
            }
        }
    }

    private fun checkNameInput() {
        validName = if (!Validation.validate(nameText)) {
            Toast.makeText(activity, "empty name please fill it", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun getUserInputData() {
        emailText = email.text.toString()
        phoneText = phone.text.toString()
        passwordText = passwordEt.text.toString()
        RePasswordText = rePasswordEt.text.toString()
        nameText = name.text.toString()
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