package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.AgendaData
import com.example.myapplication.Models.Sessions
import com.example.myapplication.R


class AgendaAdapter(modelFeedArrayList: List<Sessions>) :

    RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return agendaArrayList.size
    }

    var agendaArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.agenda_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val agendaModel = agendaArrayList[position]
        holder.agendaTitle.text = agendaModel.title
        holder.agendaDescription.text = agendaModel.title
        if (agendaModel.speakers.isNotEmpty()) {
            holder.speakersPerSession.visibility = View.VISIBLE
        } else {
            holder.speakersPerSession.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var agendaTitle: TextView = itemView.findViewById<View>(R.id.agendaTitle) as TextView
        var agendaDescription: TextView =
            itemView.findViewById<View>(R.id.agendaDescription) as TextView
        var speakersPerSession = itemView.findViewById<View>(R.id.speakersPerSession)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
