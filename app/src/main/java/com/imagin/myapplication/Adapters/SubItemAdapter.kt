package com.imagin.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.EventModels.SpeakerSession
import com.imagin.myapplication.R

class SubItemAdapter(speakers: List<SpeakerSession>) :
    RecyclerView.Adapter<SubItemAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return speakersArrayList.size
    }

    var speakersArrayList = speakers


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.speaker_sub_row, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.speakerName.text = speakersArrayList[position].name
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var speakerName: TextView = itemView.findViewById<View>(R.id.tv_sub_item_title) as TextView


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }


}
