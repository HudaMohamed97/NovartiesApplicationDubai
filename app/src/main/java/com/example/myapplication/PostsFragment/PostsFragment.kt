package com.example.myapplication.PostsFragment

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.AdapterFeed
import com.example.myapplication.Adapters.AdapterFeed.OnCommentClickListener
import com.example.myapplication.Adapters.CustomBottomSheet
import com.example.myapplication.Adapters.CustomBottomSheet.OnCommentAddedListener
import com.example.myapplication.Models.ModelFeed
import com.example.myapplication.R
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException


class PostsFragment : Fragment() {
    private lateinit var root: View
    private lateinit var postViewModel: PostViewModel
    private var dialog: ProgressDialog? = null
    private val modelFeedArrayList = arrayListOf<ModelFeed>()
    private lateinit var customBottomSheet: CustomBottomSheet
    private lateinit var adapterFeed: AdapterFeed
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.posts_fragment, container, false)
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        initSocket()
        initRecyclerView()

    }

    private fun initSocket() {
        // SocketInstance(context)
        //val mSocket = SocketInstance.getSocketInstance()

        var mSocket: Socket?

        try {
            mSocket = IO.socket("http://cat-events.cat-sw.com/")
        } catch (e: URISyntaxException) {
            throw  e
        }



        if (mSocket != null) {
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket.connect()
            if (mSocket.connected()) {
                Toast.makeText(activity, "Socket Connected!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Socket Not Connected!!", Toast.LENGTH_SHORT).show()

            }
        }

    }


    private fun hideLoader() {
        dialog?.dismiss()

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterFeed = AdapterFeed(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapterFeed
        adapterFeed.setOnCommentListener(object : OnCommentClickListener {
            override fun onLikeClicked(userModel: ModelFeed, position: Int) {
                val likes = userModel.likes
                modelFeedArrayList[position].likes = likes + 1
                adapterFeed.notifyDataSetChanged()
            }

            override fun onCommentClicked(userModel: ModelFeed, position: Int) {
                Log.i("hhhhh", "ana hena" + userModel.comments)
                customBottomSheet =
                    CustomBottomSheet(userModel, position)
                customBottomSheet.setOnCommentAddedListener(object : OnCommentAddedListener {
                    override fun onCommentAdded(userModel: ModelFeed, position: Int) {
                        val comments = userModel.comments
                        Log.i("hhhh", "ana rg3t" + comments)
                        modelFeedArrayList[position].comments = comments + 1
                        adapterFeed.notifyDataSetChanged()
                    }

                })
                fragmentManager?.let {
                    customBottomSheet.show(
                        it,
                        ""
                    )
                }

            }


        })

        populateRecyclerView()

    }

    private val onConnectError = Emitter.Listener {
        activity?.runOnUiThread {
            Log.i("hhh", "Error connecting")
            Toast.makeText(
                activity!!.applicationContext,
                "error", Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showLoader() {
        dialog = ProgressDialog(activity)
        dialog?.setMessage("Please, Wait")
        dialog?.setCancelable(false)
        dialog?.show()
    }


    private fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        recyclerView = root.findViewById(R.id.PostsRecycler)
        mainLayout.setOnClickListener {
            hideKeyboard()
        }


    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun populateRecyclerView() {

        var modelFeed = ModelFeed(
            1, 9, 2, R.drawable.ic_propic2, R.drawable.images,
            "Sajin Maharjan", "2 hrs", "The cars we drive say a lot about us."
        )
        modelFeedArrayList.add(modelFeed)
        modelFeed = ModelFeed(
            2,
            26,
            6,
            R.drawable.ic_propic2,
            0,
            "Karun Shrestha",
            "9 hrs",
            "Don't be afraid of your fears. They're not there to scare you. They're there to \n" + "let you know that something is worth it."
        )
        modelFeedArrayList.add(modelFeed)
        modelFeed = ModelFeed(
            3, 17, 5, R.drawable.ic_propic2, 0,
            "Lakshya Ram", "13 hrs", "That reflection!!!"
        )
        modelFeedArrayList.add(modelFeed)
        modelFeed = ModelFeed(
            4, 17, 2, R.drawable.ic_propic2, R.drawable.common_google_signin_btn_icon_dark,
            "Lakshya Ram", "22 hrs", "hiiiii huda!!!"
        )
        modelFeedArrayList.add(modelFeed)
        adapterFeed.notifyDataSetChanged()
    }

}