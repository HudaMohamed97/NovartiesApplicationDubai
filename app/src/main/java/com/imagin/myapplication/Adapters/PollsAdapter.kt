package com.imagin.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.PollData
import com.imagin.myapplication.R

class PollsAdapter(modelFeedArrayList: List<PollData>) :
    RecyclerView.Adapter<PollsAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return pollArrayList.size
    }

    var pollArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.poll_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pollModel = pollArrayList[position]

        holder.pollTitle.text = pollModel.title
        holder.pollTime.text = pollModel.created_at

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pollTitle: TextView = itemView.findViewById<View>(R.id.poll_title) as TextView
        var pollTime: TextView = itemView.findViewById<View>(R.id.poll_date) as TextView


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}

