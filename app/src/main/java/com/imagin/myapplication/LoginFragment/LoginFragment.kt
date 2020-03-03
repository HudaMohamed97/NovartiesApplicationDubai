package com.imagin.myapplication.LoginFragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.imagin.catapplication.utilies.Validation
import com.imagin.myapplication.Models.ResponseModelData
import com.imagin.myapplication.R
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var email: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    private var clicked = true


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

        forgetPassword.setOnClickListener{
            findNavController().navigate(R.id.action_LoginFragment_to_ForgetPassword)

        }
        showPassword.setOnClickListener {
            var cursor = passwordEt.selectionStart
            if (clicked) {
                clicked = false
                passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordEt.setSelection(cursor)
            } else {
                clicked = true
                passwordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordEt.setSelection(cursor)

            }

        }
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        val button = root.findViewById(R.id.btn_login) as Button
        email = root.findViewById(R.id.input_email)
        passwordEt = root.findViewById(R.id.input_password)
        mainLayout.setOnClickListener {
            hideKeyboard()
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()
        val saveLogin = loginPreferences.getBoolean("saveUserData", false)

        if (saveLogin) {
            email.setText(loginPreferences.getString("username", ""))
            passwordEt.setText(loginPreferences.getString("password", ""))
            chckRemember.isChecked = true
        }

        register_login.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }

        button.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (loginViewModel.validateLoginInfo(
                    email.text.toString(),
                    passwordEt.text.toString()
                )
            ) {
                callLoginRequest()
            }
        }

    }

    private fun callLoginRequest() {
        progressBar.visibility = View.VISIBLE
        loginViewModel.login(
            email.text.toString(),
            passwordEt.text.toString()
        )
        loginViewModel.getData().observe(this, Observer {
            progressBar.visibility = View.GONE
            if (it != null) {
                if (it.access_token != "") {
                    saveData(it)
                    saveUserData()
                    val bundle = Bundle()
                    bundle.putParcelable("Account", it.account)
                    findNavController().navigate(R.id.action_LoginFragment_to_Home, bundle)
                } else {
                    var error = it.token_type.replace("[", "")
                    error = error.replace("]", "")
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun checkErrorEnabled() {
        if (!Validation.validate(email.text.toString())) {
            Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
        } else if (!Validation.validateEmail(email.text.toString())) {
            Toast.makeText(
                activity,
                "Invalid Email Format Please enter valid mail",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            if (!Validation.validate(passwordEt.text.toString())) {
                Toast.makeText(activity, "empty password please fill it", Toast.LENGTH_SHORT).show()
            }
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
            loginPrefsEditor.putString("username", email.text.toString())
            loginPrefsEditor.putString("password", passwordEt.text.toString())
            loginPrefsEditor.commit()
        } else {
            loginPrefsEditor.putBoolean("saveUserData", false)
            loginPrefsEditor.commit()
        }
    }

    private fun saveData(responseModelData: ResponseModelData) {
        val token = "Bearer " + responseModelData.access_token
        loginPrefsEditor.putString("accessToken", token)
        loginPrefsEditor.putInt("userType", responseModelData.account.type)
        loginPrefsEditor.commit()
    }
}