package com.example.myapplication.RegisterFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : Fragment() {
    private lateinit var root: View
    private lateinit var email: TextInputLayout
    private lateinit var passwordEt: TextInputLayout
    private var dialog: ProgressDialog? = null
    private lateinit var FromFragment: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.register_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FromFragment = arguments?.getString("fromFragment").toString()
        textView.text = FromFragment
    }


}