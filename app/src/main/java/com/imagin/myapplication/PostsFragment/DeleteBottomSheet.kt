package com.imagin.myapplication.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.imagin.myapplication.PostsFragment.PostViewModel
import com.imagin.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.delete_bottom_sheet.*

class DeleteBottomSheet(private val postId: Int) :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    private lateinit var postViewModel: PostViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var onPostDeletdListener: OnPostDeletdListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.delete_bottom_sheet, container, false)
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        Delete_Post.setOnClickListener {
            deletePost(postId)
        }
    }

    private fun deletePost(postId: Int) {
        DeletePostProgressPar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            postViewModel.deletPost(postId, accessToken)
        }
        postViewModel.getDataDeletePost().observe(this, Observer {
            DeletePostProgressPar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Post Deleted Successfully", Toast.LENGTH_SHORT).show()
                if (onPostDeletdListener != null) {
                    onPostDeletdListener.onPostDeleted()
                    dismiss()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })
    }

    interface OnPostDeletdListener {
        fun onPostDeleted()
    }

    fun setOnPostDeletedListhener(onPostDeletdListener: OnPostDeletdListener) {
        this.onPostDeletdListener = onPostDeletdListener
    }

}


