package com.example.myapplication.PostsFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.CommentModel


class PostViewModel : ViewModel() {
     public val commentModel = MutableLiveData<CommentModel>()
    fun setCommentAdded(commentModel: CommentModel) {
        Log.i("hhhh", "setCommentAdded")

        this.commentModel.value = commentModel
    }

    fun getComment(): LiveData<CommentModel> {
        Log.i("hhhh", "getComment")
        return commentModel
    }
}
