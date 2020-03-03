package com.imagin.myapplication.ArticlesFragment

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imagin.myapplication.Adapters.ArticlesAdapter
import com.imagin.myapplication.Models.AdendaModel
import com.imagin.myapplication.Models.EventModels.Speakers
import com.imagin.myapplication.R

class ArticleFragment : Fragment() {
    private var root: View? = null
    private lateinit var articlesViewModel: ArticlesViewModel
    private var dialog: ProgressDialog? = null
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var Articles: ArrayList<Speakers>
    private lateinit var recyclerView: RecyclerView
    private lateinit var articlesAdapter: ArticlesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.article_fragment, container, false)
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        if (root != null) {
            callArticles()
        }
    }

    private fun callArticles() {
        showLoader()
        articlesViewModel.getArticles()
        articlesViewModel.getData().observe(this, Observer {
            hideLoader()
            if (it != null) {
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, " Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initRecyclerView() {
        val agendaList = ArrayList<AdendaModel>()
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        articlesAdapter = ArticlesAdapter(agendaList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = articlesAdapter
        articlesAdapter.setOnItemListener(object : ArticlesAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
            }


        })
    }

    private fun setClickListeners() {
    }


    private fun hideLoader() {
        dialog?.dismiss()

    }

    private fun showLoader() {
        dialog = ProgressDialog(activity)
        dialog?.setMessage("Please, Wait")
        dialog?.setCancelable(false)
        dialog?.show()
    }
}