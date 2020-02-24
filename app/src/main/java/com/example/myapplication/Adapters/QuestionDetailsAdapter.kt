package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.EventModels.QuestionModelOption
import com.example.myapplication.R

class QuestionDetailsAdapter(modelFeedArrayList: List<QuestionModelOption>) :
    RecyclerView.Adapter<QuestionDetailsAdapter.MyViewHolder>() {

    private var selectedPosition = -1

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return OtionsArrayList.size
    }

    var OtionsArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.answer_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val optionModel = OtionsArrayList[position]

        holder.optionRadioButton.text = optionModel.title
        if (selectedPosition == position) {
            holder.optionRadioButton.isChecked = true
        } else {
            holder.optionRadioButton.isChecked = false

        }

        holder.optionRadioButton.setOnClickListener { v ->
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
        var optionRadioButton = itemView.findViewById<View>(R.id.radioButton) as RadioButton


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
