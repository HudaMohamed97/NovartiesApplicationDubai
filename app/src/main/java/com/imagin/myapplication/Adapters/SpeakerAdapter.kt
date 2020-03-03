package com.imagin.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imagin.myapplication.Models.EventModels.Speakers
import com.imagin.myapplication.R

class SpeakerAdapter(modelFeedArrayList: List<Speakers>) :
    RecyclerView.Adapter<SpeakerAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return agendaArrayList.size
    }

    var agendaArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.speaker_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val agendaModel = agendaArrayList[position]

        holder.speakerName.text = agendaModel.name

        if (agendaModel.photo != null) {
            Glide.with(context!!).load(agendaModel.photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(holder.speakerImage)
        }

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var speakerName: TextView = itemView.findViewById<View>(R.id.speaker_name) as TextView
        var speakerImage = itemView.findViewById<ImageView>(R.id.imgProfile)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}

