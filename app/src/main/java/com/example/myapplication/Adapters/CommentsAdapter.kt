package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.CommentModel
import com.example.myapplication.R
import java.util.*


class CommentsAdapter(CommentsList: ArrayList<CommentModel>) :
    RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return commentsList.size
    }

    private var commentsList = ArrayList<CommentModel>()


    init {
        this.commentsList = CommentsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val commentList = commentsList[position]

        holder.userName.text = commentList.name
        holder.userComment.text = commentList.comment
        if (commentList.Image != 0) {
            holder.userImage.setImageResource(commentList.Image)
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById<View>(R.id.user_name) as TextView
        var userComment: TextView = itemView.findViewById<View>(R.id.user_comment) as TextView
        var userImage: ImageView = itemView.findViewById<View>(R.id.user_image) as ImageView

    }

}
