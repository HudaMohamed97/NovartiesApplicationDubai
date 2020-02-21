package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.EventModels.SpeakerSession
import com.example.myapplication.Models.Sessions
import com.example.myapplication.R
import java.util.ArrayList


class AgendaAdapter(modelFeedArrayList: List<Sessions>) :

    RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    private val viewPool = RecyclerView.RecycledViewPool()


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
        // holder.agendaTitle.text = agendaModel.title
        holder.agendaTitle.text = "jdsafnfnfnfndkfkdfk"
        //holder.agendaDescription.text = agendaModel.title
        holder.agendaDescription.text = "jfhkjfhfhjhfmdhfjdhfd"

        val list = ArrayList<SpeakerSession>()
        list.add(SpeakerSession(0, "name", "name", "", "", "", true))
        list.add(SpeakerSession(0, "huda", "name", "", "", "", true))

        holder.speakersPerSession.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(
            holder.rvSubItem.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        layoutManager.initialPrefetchItemCount = list.size
        // Create sub item view adapter
        val subItemAdapter = SubItemAdapter(list)

        holder.rvSubItem.layoutManager = layoutManager
        holder.rvSubItem.adapter = subItemAdapter
        holder.rvSubItem.setRecycledViewPool(viewPool)


        /* if (agendaModel.speakers.isNotEmpty()) {
             holder.speakersPerSession.visibility = View.VISIBLE
             val layoutManager = LinearLayoutManager(
                 holder.rvSubItem.context,
                 LinearLayoutManager.VERTICAL,
                 false
             )
             layoutManager.initialPrefetchItemCount = agendaModel.speakers.size
             // Create sub item view adapter
             val subItemAdapter = SubItemAdapter(agendaModel.speakers)

             holder.rvSubItem.layoutManager = layoutManager
             holder.rvSubItem.adapter = subItemAdapter
             holder.rvSubItem.setRecycledViewPool(viewPool)


         } else {
             holder.speakersPerSession.visibility = View.GONE
         }*/



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
        var speakersPerSession: View = itemView.findViewById<View>(R.id.speakersPerSession)
        var rvSubItem: RecyclerView = itemView.findViewById(R.id.rv_sub_item)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
