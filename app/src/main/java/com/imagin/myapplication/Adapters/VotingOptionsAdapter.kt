package com.imagin.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.OptionModel
import com.imagin.myapplication.R

class VotingOptionsAdapter(modelFeedArrayList: List<OptionModel>) :
    RecyclerView.Adapter<VotingOptionsAdapter.MyViewHolder>() {

    private var selectedPosition = -1

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return pollArrayList.size
    }

    var pollArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.vote_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pollModel = pollArrayList[position]

        holder.pollRadioButton.text = pollModel.option
        if (selectedPosition == position) {
            holder.pollRadioButton.isChecked = true
        } else {
            holder.pollRadioButton.isChecked = false

        }

        holder.pollRadioButton.setOnClickListener { v ->
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pollRadioButton = itemView.findViewById<View>(R.id.radioButton) as RadioButton


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
