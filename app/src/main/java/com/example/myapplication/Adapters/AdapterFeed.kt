package com.example.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Models.ModelFeed
import com.example.myapplication.Models.PostData
import com.example.myapplication.R
import java.util.*


class AdapterFeed(modelFeedArrayList: ArrayList<PostData>) :
    RecyclerView.Adapter<AdapterFeed.MyViewHolder>() {

    lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<PostData>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]

        holder.tvName.text = modelFeed.owner.name
        holder.tvStatus.text = modelFeed.content
        holder.tvTime.text = modelFeed.created_at
        if (modelFeed.owner.photo != null) {
            Glide.with(context!!).load(modelFeed.owner.photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(holder.imgProfile)
        }
        if (modelFeed.photo == null) {
            holder.imgviewPostpic.visibility = View.GONE
        } else {
            holder.imgviewPostpic.visibility = View.VISIBLE
            Glide.with(context!!).load(modelFeed.photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(holder.imgviewPostpic)

        }
        holder.root.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onPostClicked(position)
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvName: TextView
        var tvTime: TextView
        var tvStatus: TextView
        var imgviewPostpic = itemView.findViewById<ImageView>(R.id.imgView_postPic)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
        var root = itemView.findViewById<View>(R.id.root)

        init {
            tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
            tvTime = itemView.findViewById<View>(R.id.tv_time) as TextView
            tvStatus = itemView.findViewById<View>(R.id.tv_status) as TextView
        }
    }

    interface OnCommentClickListener {
        fun onPostClicked(position: Int)
        fun onCommentClicked(userModel: ModelFeed, position: Int)
        fun onLikeClicked(userModel: ModelFeed, position: Int)

    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }

}
