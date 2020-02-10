package com.example.myapplication.RegisterFragment

import android.app.ProgressDialog
import android.content.Context
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
import com.example.catapplication.utilies.Validation
import com.example.myapplication.LoginFragment.LoginViewModel
import com.example.myapplication.LoginFragment.RegisterViewModel
import com.example.myapplication.Models.RegisterRequestModel
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.register_login


class RegisterFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var email: TextInputLayout
    private lateinit var passwordEt: TextInputLayout
    private lateinit var phone: TextInputLayout
    private lateinit var name: TextInputLayout
    private lateinit var emailText: String
    private lateinit var passwordText: String
    private lateinit var phoneText: String
    private lateinit var nameText: String
    private var dialog: ProgressDialog? = null
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
        email = root.findViewById(R.id.Email) as TextInputLayout
        passwordEt = root.findViewById(R.id.Password) as TextInputLayout
        name = root.findViewById(R.id.Name) as TextInputLayout
        phone = root.findViewById(R.id.phone) as TextInputLayout
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
        showLoader()
        val requestModel = RegisterRequestModel(emailText, passwordText, nameText, phoneText)
        registerViewModel.register(requestModel)
        registerViewModel.getData().observe(this, Observer {
            hideLoader()
            if (it != null) {
                Toast.makeText(activity, "Register Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
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


    private fun checkErrorEnabled() {
        getUserInputData()
        checkNameInput()
        checkPhoneInput()
        checkEmailInput()
        checkPassInput()
    }

    private fun checkPassInput() {
        if (!Validation.validate(passwordText)) {
            passwordEt.isErrorEnabled = true
            passwordEt.error = "empty password please fill it"
        } else {
            passwordEt.isErrorEnabled = false
        }
    }

    private fun checkEmailInput() {
        if (!Validation.validate(emailText)) {
            email.isErrorEnabled = true
            email.error = "empty Email please fill it"
        } else {
            if (!Validation.validateEmail(emailText)) {
                email.isErrorEnabled = true
                email.error = "Invalid Email Format Please enter valid mail"
            } else {
                email.isErrorEnabled = false
            }
        }
    }

    private fun checkPhoneInput() {
        if (!Validation.validate(phoneText)) {
            phone.isErrorEnabled = true
            phone.error = "empty phone please fill it"
            validPhone = false
        } else {
            if (!Validation.isValidPhone(phoneText)) {
                phone.isErrorEnabled = true
                phone.error = "wrong phone format"
                validPhone = false
            } else {
                phone.isErrorEnabled = false
                validPhone = true
            }
        }
    }

    private fun checkNameInput() {
        if (!Validation.validate(nameText)) {
            name.isErrorEnabled = true
            name.error = "empty name please fill it"
            validName = false
        } else {
            if (!Validation.isValidUserName(nameText)) {
                name.isErrorEnabled = true
                name.error = "wrong name format"
                validName = false
            } else {
                name.isErrorEnabled = false
                validName = true
            }
        }
    }

    private fun getUserInputData() {
        emailText = email.editText?.text.toString()
        phoneText = phone.editText?.text.toString()
        passwordText = passwordEt.editText?.text.toString()
        nameText = name.editText?.text.toString()
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