package com.imagin.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.AgendaRatingData
import com.imagin.myapplication.R

class EventAdapterRating(modelFeedArrayList: List<AgendaRatingData>) :
    RecyclerView.Adapter<EventAdapterRating.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return questionArrayList.size
    }

    var questionArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.question_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val questionModel = questionArrayList[position]
        holder.Questiontitle.text = questionModel.title

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Questiontitle: TextView = itemView.findViewById(R.id.question_title)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}


