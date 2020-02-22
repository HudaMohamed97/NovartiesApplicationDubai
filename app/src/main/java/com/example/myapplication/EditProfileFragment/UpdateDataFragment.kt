package com.example.myapplication.EditProfileFragment

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.Account
import com.example.myapplication.R
import kotlinx.android.synthetic.main.edit_profile.*
import java.io.File
import java.io.FileNotFoundException
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat.requestPermissions
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission


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
    private var fileUri: String = ""
    private var matched = false
    private lateinit var loginPreferences: SharedPreferences
    private val PERMISSION_REQUEST_CODE = 1


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data?.data
                fileUri = selectedImage?.let { getPath(it) }.toString()
                imgProfile.setImageURI(selectedImage)
            }

    }

    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = activity!!.contentResolver.query(uri, projection, null, null, null);
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor?.moveToFirst()
        if (column_index != null) {
            cursor.getString(column_index)
        }

        return column_index?.let { cursor.getString(it) }!!
    }

    /*private fun requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                activity!!,
                "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            requestPermissions(
                activity!!,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }*/

    private fun isStoragePermissionGranted(): Boolean {
        if (SDK_INT >= Build.VERSION_CODES.M) {
            return if (checkSelfPermission(
                    activity!!,
                    READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                requestPermissions(
                    activity!!,
                    arrayOf(READ_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()


            //resume tasks needing this permission
        } else {
            Toast.makeText(activity, "not access", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setListeners() {
        imgProfile.setOnClickListener {
            isStoragePermissionGranted()
            val photoPickerIntent = Intent(ACTION_PICK)
            photoPickerIntent.type = "image/*"
            this.startActivityForResult(photoPickerIntent, 1)
        }

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
            if (fileUri == "") {
                fileUri = account.photo
            }
            updateDataModel.updateData(fileUri, emailText, nameText, accessToken)
        }
        updateDataModel.getData().observe(this, Observer {
            editDataProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Updated Successfuly", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_back_to_login)

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
