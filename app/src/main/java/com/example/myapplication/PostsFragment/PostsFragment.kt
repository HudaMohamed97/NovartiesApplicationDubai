package com.example.myapplication.PostsFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.AdapterFeed
import com.example.myapplication.Adapters.AdapterFeed.OnCommentClickListener
import com.example.myapplication.Adapters.CustomBottomSheet
import com.example.myapplication.Adapters.CustomBottomSheet.OnCommentAddedListener
import com.example.myapplication.Models.ModelFeed
import com.example.myapplication.Models.PostData
import com.example.myapplication.R
import kotlinx.android.synthetic.main.posts_fragment.*


class PostsFragment : Fragment() {
    private lateinit var root: View
    private lateinit var postViewModel: PostViewModel
    private val modelFeedArrayList = arrayListOf<PostData>()
    private lateinit var customBottomSheet: CustomBottomSheet
    private lateinit var adapterFeed: AdapterFeed
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0


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
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callPosts(1, false)

    }

    private fun callPosts(page: Int, fromLoadMore: Boolean) {
        if (fromLoadMore) {
            postLoadProgressBar.visibility = View.VISIBLE
        } else {
            PostsProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            postViewModel.getPosts(page, accessToken)
        }
        postViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                postLoadProgressBar.visibility = View.GONE
            } else {
                PostsProgressBar.visibility = View.GONE
            }
            if (it != null) {
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                adapterFeed.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterFeed = AdapterFeed(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapterFeed
        adapterFeed.setOnCommentListener(object : OnCommentClickListener {
            override fun onLikeClicked(userModel: ModelFeed, position: Int) {
                /* val likes = userModel.likes
                 modelFeedArrayList[position].likes = likes + 1
                 adapterFeed.notifyDataSetChanged()*/
            }

            override fun onCommentClicked(userModel: ModelFeed, position: Int) {
                customBottomSheet =
                    CustomBottomSheet(userModel, position)
                customBottomSheet.setOnCommentAddedListener(object : OnCommentAddedListener {
                    override fun onCommentAdded(userModel: ModelFeed, position: Int) {
                        /*val comments = userModel.comments
                        Log.i("hhhh", "ana rg3t" + comments)
                        modelFeedArrayList[position].comments = comments + 1
                        adapterFeed.notifyDataSetChanged()*/
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

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callPosts(currentPageNum, true)

                    }
                }
            }
        })

    }

    private fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        recyclerView = root.findViewById(R.id.PostsRecycler)
        mainLayout.setOnClickListener {
            hideKeyboard()
        }
        val logOutButton = root.findViewById(R.id.logOutButton) as ImageView
        val backButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
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

}