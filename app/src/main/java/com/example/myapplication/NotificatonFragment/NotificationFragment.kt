package com.example.myapplication.NotificatonFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.NotificationAdapter
import com.example.myapplication.Models.NotificationModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.notification_fragment.*


class NotificationFragment : Fragment() {
    private var root: View? = null
    private lateinit var notificationViewModel: NotificationViewModel
    private val list = arrayListOf<NotificationModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationProgressBar: ProgressBar
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var loginPreferences: SharedPreferences
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
        return if (root != null) {
            currentPageNum = 1
            lastPageNum = 0
            root
        } else {
            root = inflater.inflate(R.layout.notification_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callNotificationRequest(1, false)
    }

    private fun callNotificationRequest(pageNumber: Int, fromLoadMore: Boolean) {
        if (fromLoadMore) {
            notifictionMoreLoadProgressBar.visibility = View.VISIBLE
        } else {
            notificationProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            notificationViewModel.getNotification(pageNumber, accessToken)
        }
        notificationViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                notifictionMoreLoadProgressBar.visibility = View.GONE
            } else {
                notificationProgressBar.visibility = View.GONE
            }
            if (it != null) {
                list.clear()
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    list.add(data)
                }
                if (list.size == 0) {
                    Toast.makeText(activity, "There is No Notification", Toast.LENGTH_SHORT).show()
                }
                notificationAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        notificationAdapter = NotificationAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = notificationAdapter
        notificationAdapter.setOnItemListener(object : NotificationAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                when {
                    list[position].type == "post" -> findNavController().navigate(R.id.action_Notification_to_PostsFragment)
                    list[position].type == "poll" -> findNavController().navigate(R.id.action_Notification_to_PollFragment)
                    list[position].type == "speaker" -> findNavController().navigate(R.id.action_Notification_to_SpeakersFragment)
                    list[position].type == "question" -> findNavController().navigate(R.id.action_Notification_to_QuestionFragment)
                    list[position].type == "agenda" -> findNavController().navigate(R.id.action_Notification_to_AgendaFragment)
                    list[position].type == "event" -> findNavController().navigate(R.id.action_Notification_to_SessionFragment)
                    else -> findNavController().navigateUp()
                }
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callNotificationRequest(currentPageNum, true)

                    }
                }
            }
        })

    }

    private fun setClickListeners() {
        recyclerView = root?.findViewById(R.id.speakerRecycler)!!
        notificationProgressBar = root?.findViewById(R.id.notificationProgressBar)!!
        val backButton = root?.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}