package com.imagin.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.NotificationModel
import com.imagin.myapplication.R

class NotificationAdapter(modelFeedArrayList: List<NotificationModel>) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return agendaArrayList.size
    }

    var agendaArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notificaton_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val agendaModel = agendaArrayList[position]
        holder.noticationTitle.text = agendaModel.title
        holder.notificationMessage.text = agendaModel.message
        holder.notificationDate.text = agendaModel.created_at

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noticationTitle: TextView =
            itemView.findViewById<View>(R.id.notification_title) as TextView
        var notificationMessage = itemView.findViewById<TextView>(R.id.notification_message)
        var notificationDate = itemView.findViewById<TextView>(R.id.notification_date)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}

