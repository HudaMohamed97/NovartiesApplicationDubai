package com.example.myapplication.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.ModelFeed
import com.example.myapplication.R
import java.util.*


class AdapterFeed(modelFeedArrayList: ArrayList<ModelFeed>) :
    RecyclerView.Adapter<AdapterFeed.MyViewHolder>() {

    lateinit var onCommentClickListener: OnCommentClickListener

    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<ModelFeed>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]

        holder.tvName.text = modelFeed.name
        holder.tvTime.text = modelFeed.time
        holder.tv_likes.text = (modelFeed.likes).toString()
        Log.i("hhh", "likes" + modelFeed.likes)
        holder.tvComments.text = modelFeed.comments.toString() + " comments"
        holder.tvStatus.text = modelFeed.status
        if (modelFeed.postpic == 0) {
            holder.imgviewPostpic.visibility = View.GONE
        } else {
            holder.imgviewPostpic.visibility = View.VISIBLE
            holder.imgviewPostpic.setImageResource(modelFeed.postpic)

        }
        holder.commentView.setOnClickListener {
            if (onCommentClickListener != null && position != RecyclerView.NO_POSITION)
                onCommentClickListener.onCommentClicked(modelFeedArrayList[position], position)
        }
        holder.likeView.setOnClickListener {

            if (onCommentClickListener != null && position != RecyclerView.NO_POSITION)
                onCommentClickListener.onLikeClicked(modelFeedArrayList[position], position)
            holder.tvComments.text = modelFeed.comments.toString() + " comments"
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvName: TextView
        var commentView: View
        var likeView: View
        var tvTime: TextView
        var tv_likes: TextView
        var tvComments: TextView
        var tvStatus: TextView
        var imgView_proPic: ImageView =
            itemView.findViewById<View>(R.id.imgView_proPic) as ImageView
        var imgviewPostpic: ImageView =
            itemView.findViewById<View>(R.id.imgView_postPic) as ImageView

        init {

            tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
            tvTime = itemView.findViewById<View>(R.id.tv_time) as TextView
            tv_likes = itemView.findViewById<View>(R.id.tv_like) as TextView
            tvComments = itemView.findViewById<View>(R.id.tv_comment) as TextView
            tvStatus = itemView.findViewById<View>(R.id.tv_status) as TextView
            commentView = itemView.findViewById<View>(R.id.CommentView) as View
            likeView = itemView.findViewById<View>(R.id.likeView) as View
        }
    }

    interface OnCommentClickListener {
        fun onCommentClicked(userModel: ModelFeed, position: Int)
        fun onLikeClicked(userModel: ModelFeed, position: Int)

    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener
    }

}
