package com.example.myapplication.PostsFragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.example.myapplication.Models.PostOwner
import com.example.myapplication.R
import kotlinx.android.synthetic.main.edit_profile.*
import kotlinx.android.synthetic.main.posts_fragment.*


class PostsFragment : Fragment() {
    private lateinit var root: View
    private lateinit var postViewModel: PostViewModel
    private val modelFeedArrayList = arrayListOf<PostData>()
    private lateinit var customBottomSheet: CustomBottomSheet
    private lateinit var adapterFeed: AdapterFeed
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    private var type: Int = -1
    var mHasReachedBottomOnce = false
    private var fileUri: String = ""
    private lateinit var selectedImage: Uri
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
        type = loginPreferences.getInt("userType", -1)
        if (type == 1) {
            addPost.visibility = View.VISIBLE
        } else {
            addPost.visibility = View.GONE
        }
        setClickListeners()
        initRecyclerView()
        callPosts(1, false, false)

    }

    private fun callPosts(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
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
            if (fromRefresh) {
                currentPageNum = 1
                modelFeedArrayList.clear()
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

    private fun setPosts() {
        PostsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            if (fileUri == "") {
                fileUri = ""
            }
            postViewModel.setPost(post_layout.text.toString(), fileUri, accessToken)
        }
        postViewModel.getDataAddPost().observe(this, Observer {
            PostsProgressBar.visibility = View.GONE
            if (it != null) {
                post_layout.setText("")
                Toast.makeText(activity, "Post Added Successfully", Toast.LENGTH_SHORT).show()
                postImageSelected.visibility = View.GONE
                /*modelFeedArrayList.add(
                    0, PostData(
                        0, post_layout.text.toString(), selectedImage.toString(), 0,
                        PostOwner()
                    )
                )
                adapterFeed.notifyItemInserted(0)
                recyclerView.smoothScrollToPosition(0)*/
                callPosts(1, false, true)
            } else {
                postImageSelected.visibility = View.GONE
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deletePost(postId: Int) {
        PostsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            postViewModel.deletPost(postId, accessToken)
        }
        postViewModel.getDataDeletePost().observe(this, Observer {
            PostsProgressBar.visibility = View.GONE
            if (it != null) {
                post_layout.setText("")
                Toast.makeText(activity, "Post Deleted Successfully", Toast.LENGTH_SHORT).show()
                postImageSelected.visibility = View.GONE
                callPosts(1, false, true)
            } else {
                postImageSelected.visibility = View.GONE
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()


            //resume tasks needing this permission
        } else {
            Toast.makeText(activity, "not access", Toast.LENGTH_SHORT).show()
        }
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterFeed = AdapterFeed(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapterFeed
        adapterFeed.setOnCommentListener(object : OnCommentClickListener {
            override fun onPostClicked(position: Int) {
                val postId = modelFeedArrayList[position].id
                if (type == 1) {
                    deletePost(postId)
                } else {
                    Toast.makeText(activity, "not access to delete this post", Toast.LENGTH_SHORT)
                        .show()

                }


            }

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
                        callPosts(currentPageNum, true, false)

                    }
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data?.data!!
                fileUri = selectedImage?.let { getPath(it) }.toString()
                postImageSelected.visibility = View.VISIBLE
                postImageSelected.setImageURI(selectedImage)
            }

    }

    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = activity!!.contentResolver.query(uri, projection, null, null, null);
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor?.moveToFirst()
        if (column_index != null) {
            cursor.getString(column_index)
        }

        return column_index?.let { cursor.getString(it) }!!
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
        camera.setOnClickListener {
            if (isStoragePermissionGranted()) {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                this.startActivityForResult(photoPickerIntent, 1)
            } else {
                Toast.makeText(
                    activity,
                    "Please Enable Access Storage",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        postImage.setOnClickListener {
            if (post_layout.text.toString().isEmpty()) {
                Toast.makeText(
                    activity,
                    "Please Write SomeThing To Post it , Thanks",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                hideKeyboard()
                setPosts()
            }

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