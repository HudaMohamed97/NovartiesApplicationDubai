package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.Sessions
import com.example.myapplication.R


class SessionsRatingAdapter(modelFeedArrayList: List<Sessions>) :
    RecyclerView.Adapter<SessionsRatingAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return sessionsArrayList.size
    }

    var sessionsArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rate_session_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sessionModel = sessionsArrayList[position]
        holder.sessionTitle.text = sessionModel.title
        val date = sessionModel.time_from + "  " + sessionModel.time_to
        holder.sessionDate.text = date
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionTitle: TextView = itemView.findViewById<View>(R.id.session_title) as TextView
        var sessionDate: TextView = itemView.findViewById<View>(R.id.session_date) as TextView
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}