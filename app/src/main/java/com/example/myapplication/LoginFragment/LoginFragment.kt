package com.example.myapplication.LoginFragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.ResponseModelData
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var email: TextInputLayout
    private lateinit var passwordEt: TextInputLayout
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    private var type: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.login_fragment, container, false)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    override fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        val button = root.findViewById(R.id.btn_login) as Button
        email = root.findViewById(R.id.email) as TextInputLayout
        passwordEt = root.findViewById(R.id.password) as TextInputLayout
        mainLayout.setOnClickListener {
            hideKeyboard()
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()
        val saveLogin = loginPreferences.getBoolean("saveLogin", false)

        if (saveLogin) {
            emailText.setText(loginPreferences.getString("username", ""))
            passText.setText(loginPreferences.getString("password", ""))
            chckRemember.isChecked = true
        }

        register_login.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }

        button.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (loginViewModel.validateLoginInfo(
                    email.editText?.text.toString(),
                    passwordEt.editText?.text.toString()
                )
            ) {
                email.isErrorEnabled = false
                passwordEt.isErrorEnabled = false
                callLoginRequest()
            }
        }

    }

    private fun callLoginRequest() {
        progressBar.visibility = View.VISIBLE
        type = radioType.checkedRadioButtonId
        if (radioAttende.isChecked) {
            if (radioSpeaker.isChecked) {
                radioSpeaker.isChecked = false
            }
            type = 1
        } else {
            radioAttende.isChecked = false
            type = 2
        }
        loginViewModel.login(
            email.editText?.text.toString(),
            passwordEt.editText?.text.toString(), type
        )
        loginViewModel.getData().observe(this, Observer {
            progressBar.visibility = View.GONE
            if (it != null) {
                if (it.access_token != "") {
                    saveData(it)
                    findNavController().navigate(R.id.action_LoginFragment_to_Home)
                } else {
                    var error = it.error.replace("[", "")
                    error = error.replace("]", "")
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun checkErrorEnabled() {
        if (!Validation.validate(email.editText?.text.toString())) {
            email.isErrorEnabled = true
            email.error = "empty Email please fill it"
        } else {
            if (!Validation.validateEmail(email.editText?.text.toString())) {
                email.isErrorEnabled = true
                email.error = "Invalid Email Formate Please enter valid mail"
            } else {
                email.isErrorEnabled = false
            }
        }
        if (!Validation.validate(passwordEt.editText?.text.toString())) {
            passwordEt.isErrorEnabled = true
            passwordEt.error = "empty password please fill it"
        } else {
            passwordEt.isErrorEnabled = false
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

    private fun saveUserData() {
        if (chckRemember.isChecked) {
            loginPrefsEditor.putBoolean("saveUserData", true)
            loginPrefsEditor.putString("username", "test")
            loginPrefsEditor.putString("password", "12345")
            loginPrefsEditor.commit()
        } else {
            loginPrefsEditor.clear()
            loginPrefsEditor.commit()
        }
    }

    private fun saveData(responseModelData: ResponseModelData) {
        val token = "Bearer " + responseModelData.access_token
        loginPrefsEditor.putString("accessToken", token)
        loginPrefsEditor.putInt("type", type)
        loginPrefsEditor.commit()
    }
}