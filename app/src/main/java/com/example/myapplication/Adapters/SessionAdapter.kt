package com.example.myapplication.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.SessionsData
import com.example.myapplication.R
import java.util.*


class SessionAdapter(modelFeedArrayList: ArrayList<SessionsData>) :

    RecyclerView.Adapter<SessionAdapter.MyViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return sessionArrayList.size
    }

    var sessionArrayList = ArrayList<SessionsData>()


    init {
        this.sessionArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_item, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sessionModel = sessionArrayList[position]
        holder.sessionDate.text = sessionModel.date
        holder.sessionName.text = sessionModel.city
        holder.sessionAddress.text = sessionModel.address
        holder.sessionLocation.setOnClickListener {
            if (sessionModel.lat != null && sessionModel.lng != null) {
                val lat = sessionModel.lat.toDouble()
                val lng = sessionModel.lng.toDouble()
                val uri = String.format(
                    Locale.ENGLISH,
                    "http://maps.google.com/maps?q=loc:%f,%f",
                    lat,
                    lng
                )
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context?.startActivity(intent)
            } else {
                Toast.makeText(context, "No Location Found", Toast.LENGTH_SHORT).show()

            }
        }


        if (sessionModel.active) {
            holder.sessionLocation.visibility = View.VISIBLE
            holder.radioGroup.visibility = View.VISIBLE
            holder.dimmedLayout.visibility = View.GONE
        } else {
            holder.sessionLocation.visibility = View.GONE
            holder.radioGroup.visibility = View.GONE
            holder.dimmedLayout.visibility = View.VISIBLE
        }



        holder.radioAttend.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position, 1)
            }
        }
        holder.radioNotAttend.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position, 0)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sessionName: TextView = itemView.findViewById<View>(R.id.sessionName) as TextView
        var sessionAddress: TextView = itemView.findViewById<View>(R.id.sessionAddress) as TextView
        var sessionLocation = itemView.findViewById<Button>(R.id.locationSession) as Button
        var radioGroup = itemView.findViewById<View>(R.id.radioGroup) as RadioGroup
        var dimmedLayout = itemView.findViewById<View>(R.id.dimmed_layout) as View
        var sessionDate: TextView = itemView.findViewById<View>(R.id.sessionDate) as TextView
        var radioAttend: TextView = itemView.findViewById<View>(R.id.radioButton1) as TextView
        var radioNotAttend: TextView = itemView.findViewById<View>(R.id.radioButton2) as TextView


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, statues: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}
