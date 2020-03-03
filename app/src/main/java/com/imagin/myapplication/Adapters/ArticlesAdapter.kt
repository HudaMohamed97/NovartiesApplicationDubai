package com.imagin.myapplication.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Models.AdendaModel
import com.imagin.myapplication.R
import java.util.ArrayList

class ArticlesAdapter(modelFeedArrayList: ArrayList<AdendaModel>) :
    RecyclerView.Adapter<ArticlesAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun getItemCount(): Int {
        return articlesArrayList.size
    }

    var articlesArrayList = ArrayList<AdendaModel>()


    init {
        this.articlesArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articleModel = articlesArrayList[position]
        holder.articleName.text = articleModel.speakerName

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var articleName: TextView = itemView.findViewById<View>(R.id.article_name) as TextView


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}

