package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.EventModels.Speakers
import com.example.myapplication.R

class SpeakerAdapter(modelFeedArrayList: List<Speakers>) :
    RecyclerView.Adapter<SpeakerAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return agendaArrayList.size
    }

    var agendaArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.speaker_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val agendaModel = agendaArrayList[position]

        holder.speakerName.text = agendaModel.name

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var speakerName: TextView = itemView.findViewById<View>(R.id.speaker_name) as TextView


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}

