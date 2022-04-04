package com.example.firebase.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.firebase.R
import com.example.firebase.manager.DatabaseHandler
import com.example.firebase.manager.DatabaseManager
import com.example.firebase.model.Post
import com.sangcomz.fishbun.FishBun

class CreateActivity : BaseActivity() {
    lateinit var iv_photo: ImageView
    var pickedImage: Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }

    fun initViews() {
        var iv_close = findViewById<ImageView>(R.id.iv_close)
        var et_title = findViewById<EditText>(R.id.et_title)
        var et_body = findViewById<EditText>(R.id.et_body)
        var b_create = findViewById<Button>(R.id.b_create)

        iv_close.setOnClickListener {
            finish()
        }
        b_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            val post = Post(title, body)
            storeDatabase(post)
        }
    }


    fun storeDatabase(post: Post) {
        DatabaseManager.storePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                Log.d("@@@", "post is saved")
                dismissLoading()
                finishIntent()
            }

            override fun onError() {
                dismissLoading()
                Log.d("@@@", "post is not saved")
            }
        })
    }

    fun finishIntent() {
        val returnIntent = intent
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    fun pickUserPhoto(){
        FishBun.with(this)
            .setImageAdapter()
    }
}