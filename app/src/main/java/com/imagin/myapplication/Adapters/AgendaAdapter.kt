package com.imagin.myapplication.Adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.Sessions
import com.imagin.myapplication.R


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
        holder.agendaName.text = agendaModel.title
        holder.agendaLoacation.text = agendaModel.location
        holder.agendaTimeFrom.text = agendaModel.time_from
        holder.agendaTimeTo.text = agendaModel.time_to
        if (agendaModel.speakers.isNotEmpty()) {
            holder.agendaSpeakers.visibility = View.VISIBLE
            holder.agendaSpeakers.text = ""
            for (speaker in agendaModel.speakers) {
                holder.agendaSpeakers.append(speaker.name + ".")
            }

        } else {
            holder.agendaSpeakers.visibility = View.GONE

        }
        if (agendaModel.desc != null) {
            holder.agendaContent.visibility = View.VISIBLE
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                holder.agendaContent.text = Html.fromHtml(
                    agendaModel.desc, Html.FROM_HTML_MODE_LEGACY
                )
                holder.agendaContent.append(
                    "\n" + "" +
                            "       "
                )
            } else {
                holder.agendaContent.text = Html.fromHtml(
                    agendaModel.desc
                )
                holder.agendaContent.append(
                    "\n" + "" +
                            "       "
                )
            }
        } else {
            holder.agendaContent.visibility = View.GONE
        }

        /*if (agendaModel.speakers.isNotEmpty()) {
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
        var agendaTimeFrom: TextView = itemView.findViewById<View>(R.id.agendatimefrom) as TextView
        var agendaTimeTo: TextView = itemView.findViewById<View>(R.id.agendatimeto) as TextView
        var agendaLoacation: TextView = itemView.findViewById<View>(R.id.agendalocation) as TextView
        var agendaContent: TextView = itemView.findViewById<View>(R.id.agendaContent) as TextView
        var agendaSpeakers: TextView =
            itemView.findViewById<View>(R.id.agendaSpeakers) as TextView
        var agendaName: TextView =
            itemView.findViewById<View>(R.id.agendaName) as TextView
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
