package com.example.myapplication.RatingFrgament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R

class RatingFragment : Fragment() {
    private lateinit var root: View
    private lateinit var ratingFragmentViewModel: RatingFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.rating_fragment, container, false)
        ratingFragmentViewModel =
            ViewModelProviders.of(this).get(RatingFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
    }
}