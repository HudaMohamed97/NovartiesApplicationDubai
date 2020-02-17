package com.example.myapplication.Adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.CommentModel
import com.example.myapplication.Models.ModelFeed
import com.example.myapplication.PostsFragment.PostViewModel
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.comment_fragment.*

class CustomBottomSheet(private var model: ModelFeed?, private val position: Int?) :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    private val Comments = arrayListOf<String>()
    private lateinit var postViewModel: PostViewModel
    lateinit var onCommentClickListener: OnCommentAddedListener
    private val commentsArrayList = arrayListOf<CommentModel>()
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        root = inflater.inflate(R.layout.comment_fragment, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = root.findViewById(R.id.Comments_list)
        initRecyclerView()
        Log.i("hhhh", "ana bottom shhet" + (model?.comments))
        send_button.setOnClickListener {
            model?.let { it1 ->
                if (onCommentClickListener != null) {
                    if (position != null) {
                        val commentModel = CommentModel(
                            model!!.name, message_input.text.toString(), 0
                        )
                        commentsArrayList.add(commentModel)
                        commentsAdapter.notifyDataSetChanged()

                        onCommentClickListener.onCommentAdded(it1, position)
                    }
                }
            }
            Log.i("hhhh", "in send" + (model?.comments))

        }

    }

    interface OnCommentAddedListener {
        fun onCommentAdded(userModel: ModelFeed, position: Int)
    }

    fun setOnCommentAddedListener(onCommentClickListener: OnCommentAddedListener) {
        this.onCommentClickListener = onCommentClickListener
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        commentsAdapter = CommentsAdapter(commentsArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = commentsAdapter
        populateRecyclerView()


    }


    private fun populateRecyclerView() {
        var commentModel = CommentModel(
            "huda", "hi it is so good", 0
        )
        commentsArrayList.add(commentModel)
        commentModel = CommentModel(
            "hassan", "hi huda", 0
        )
        commentsArrayList.add(commentModel)
        commentModel = CommentModel(
            "lolo", "hi hudajjjj", 0
        )
        commentsArrayList.add(commentModel)
        commentsAdapter.notifyDataSetChanged()
    }


}


