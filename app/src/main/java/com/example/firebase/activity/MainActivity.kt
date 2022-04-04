package com.example.firebase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.*
import com.example.firebase.activity.BaseActivity
import com.example.firebase.activity.CreateActivity
import com.example.firebase.adapter.PostAdapter
import com.example.firebase.helper.RecyclerItemTouchHelper
import com.example.firebase.manager.AuthManager
import com.example.firebase.manager.DatabaseHandler
import com.example.firebase.manager.DatabaseManager
import com.example.firebase.model.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(this, 1))

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val itemTouchHelperCallbackLeft = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListner{
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int,
                position: Int,
            ) {
                Log.d("@@@", "@@@position " + position)
            }

        })

        val itemTouchHelperCallbackRight = RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListner{
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int,
                position: Int,
            ) {
                Log.d("@@@", "@@@position " + position)
            }

        })

        ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(recyclerView)
        ItemTouchHelper(itemTouchHelperCallbackRight).attachToRecyclerView(recyclerView)

        var iv_logout = findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(this)
        }

        var fab_create = findViewById<FloatingActionButton>(R.id.fab_create)
        fab_create.setOnClickListener {
            callCreateActivity()
        }

        apiLoadPosts()
    }

    fun apiLoadPosts() {
        showLoading(this)
        DatabaseManager.apiLoadPosts(object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError() {
                dismissLoading()
            }
        })
    }


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Load all posts...
            apiLoadPosts()
        }
    }

    fun callCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        resultLauncher.launch(intent)
    }

    fun refreshAdapter(posts: ArrayList<Post>) {
        val adapter = PostAdapter(this, posts)
        recyclerView.adapter = adapter
    }

    fun apiDeletePost(post: Post) {
        DatabaseManager.apiDeletePost(post, object: DatabaseHandler{
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                apiLoadPosts()
            }

            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }
}