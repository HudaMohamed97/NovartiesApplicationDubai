package com.example.myapplication.EditProfileFragment

import android.content.Context
import android.content.SharedPreferences
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
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.Account
import com.example.myapplication.R
import kotlinx.android.synthetic.main.edit_profile.*

class UpdateDataFragment : Fragment() {
    private lateinit var root: View
    private lateinit var updateDataModel: UpdateDataViewModel
    private lateinit var email: EditText
    private lateinit var passwordEt: EditText
    private lateinit var rePasswordEt: EditText
    private lateinit var name: EditText
    private lateinit var emailText: String
    private lateinit var passwordText: String
    private lateinit var rePasswordText: String
    private lateinit var nameText: String
    private lateinit var account: Account
    private var matched = false
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.edit_profile, container, false)
        updateDataModel = ViewModelProviders.of(this).get(UpdateDataViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        account = arguments?.getParcelable("ACCOUNT")!!
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        email = root.findViewById(R.id.input_email)
        passwordEt = root.findViewById(R.id.input_password)
        rePasswordEt = root.findViewById(R.id.input_RePassword)
        name = root.findViewById(R.id.input_Name)
        setListeners()
    }

    private fun setListeners() {
        updateData.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (emailText.isNotEmpty() && Validation.validateEmail(emailText) && (nameText.isNotEmpty())
            ) {
                callUpdateRequest()
            }
        }
        changePassword.setOnClickListener {
            checkErrorEnabledPassword()
            hideKeyboard()
            if (passwordText.isNotEmpty() && matched) {
                // callUpdatePassword()
            }
        }

    }

    private fun callUpdateRequest() {
        editDataProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            updateDataModel.updateData(emailText, nameText, accessToken)
        }
        updateDataModel.getData().observe(this, Observer {
            editDataProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Updated Successfuly", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun callUpdatePassword() {
        editDataProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            updateDataModel.updatePassword(passwordText, accessToken)
        }
        updateDataModel.getPasswordData().observe(this, Observer {
            editDataProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Updated Successfuly", Toast.LENGTH_SHORT).show()
                // to navigate to login Screen 
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun checkErrorEnabled() {
        getUserInputData()
        isPasswordMatched()
        validate()
    }

    private fun checkErrorEnabledPassword() {
        getUserInputData()
        isPasswordMatched()
        validatePassword()

    }

    private fun isPasswordMatched() {
        matched = passwordText == rePasswordText
    }


    private fun validate() {
        if (!Validation.validate(emailText)) {
            Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
        } else if (!Validation.validateEmail(emailText)) {
            Toast.makeText(
                activity,
                "Invalid Email Format Please enter valid mail",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!Validation.validate(nameText)) {
            Toast.makeText(activity, "empty name please fill it", Toast.LENGTH_SHORT).show()

        }
    }


    private fun validatePassword() {
        if (!Validation.validate(passwordText)) {
            Toast.makeText(activity, "empty password please fill it", Toast.LENGTH_SHORT).show()

        } else if (!Validation.validate(rePasswordText)) {
            Toast.makeText(activity, "please reconfirm your Password", Toast.LENGTH_SHORT)
                .show()

        } else if (passwordEt.length() < 6 || rePasswordText.length < 6) {
            Toast.makeText(
                activity,
                "password must be at least 6 characters",
                Toast.LENGTH_SHORT
            )
                .show()
        } else if (!matched) {
            Toast.makeText(
                activity,
                "password and confirmed Password not matched",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun getUserInputData() {
        emailText = email.text.toString()
        passwordText = passwordEt.text.toString()
        rePasswordText = rePasswordEt.text.toString()
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
