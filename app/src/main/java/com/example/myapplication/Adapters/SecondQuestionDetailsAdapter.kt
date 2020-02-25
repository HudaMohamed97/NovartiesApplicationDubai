package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.EventModels.QuestionModelOption
import com.example.myapplication.R

class SecondQuestionDetailsAdapter(modelFeedArrayList: List<QuestionModelOption>) :
    RecyclerView.Adapter<SecondQuestionDetailsAdapter.MyViewHolder>() {

    private var selectedPosition = -1

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return OtionsArrayList.size
    }

    var OtionsArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.second_row_details_fragment, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val optionModel = OtionsArrayList[position]

        holder.second_question_title.text = optionModel.title

        holder.itemView.setOnClickListener { v ->
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var second_question_title = itemView.findViewById<TextView>(R.id.second_question_title)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
