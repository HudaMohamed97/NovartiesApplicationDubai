package com.imagin.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.OptionModel
import com.imagin.myapplication.R

class MultipleChoiceAdapterEvent(modelFeedArrayList: List<OptionModel>) :
    RecyclerView.Adapter<MultipleChoiceAdapterEvent.MyViewHolder>() {

    private var selectedPosition = -1
    private val list = arrayListOf<Int>()


    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return OtionsArrayList.size
    }

    var OtionsArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.check_box_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rateOption = OtionsArrayList[position]
        holder.list_view_item_text.text = rateOption.option
        holder.list_view_item_checkbox.setOnClickListener {
            if (holder.list_view_item_checkbox.isChecked) {
                list.add(OtionsArrayList[position].id)
            } else {
                list.remove(OtionsArrayList[position].id)
            }
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(list)
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var list_view_item_text = itemView.findViewById<TextView>(R.id.list_view_item_text)
        var list_view_item_checkbox = itemView.findViewById<CheckBox>(R.id.list_view_item_checkbox)
    }

    interface OnItemClickListener {
        fun onItemClicked(list: ArrayList<Int>)
    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
