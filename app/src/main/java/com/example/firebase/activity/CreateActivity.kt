package com.example.firebase.activity

import android.app.Activity
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.firebase.R
import com.example.firebase.manager.DatabaseHandler
import com.example.firebase.manager.DatabaseManager
import com.example.firebase.manager.StorageHandler
import com.example.firebase.manager.StorageManager
import com.example.firebase.model.Post
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader
import java.io.File
import android.graphics.Bitmap
import com.sangcomz.fishbun.MimeType


class CreateActivity : BaseActivity() {
    lateinit var iv_photo: ImageView
    var pickedPhoto: Uri? = null
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
        iv_photo = findViewById(R.id.iv_photo)

        val iv_camera = findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            pickUserPhoto()
            //albumPhoto()
        }

        iv_close.setOnClickListener {
            finish()
        }
        b_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            val post = Post(title, body)
            storePost(post)
        }
    }

    fun storePost(post: Post) {
        if (pickedPhoto != null) {
            storeStorage(post)
        } else {
            storeDatabase(post)
        }
    }

    private fun storeStorage(post: Post) {
        showLoading(this)
        StorageManager.uploadPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                post.img = imgUrl
                storeDatabase(post)
            }

            override fun onError(exception: Exception) {
                storeDatabase(post)
            }
        })
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

    fun pickUserPhoto() {
            FishBun.with(this)
                .setImageAdapter(GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setCamera(true)
                .exceptGif(true)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .exceptGif(true)
                .setReachLimitAutomaticClose(true)
                .setAllViewTitle("All")
                .setMenuAllDoneText("All Done")
                .exceptMimeType(listOf(MimeType.GIF))
                .hasCameraInPickerPage(true)
                .setButtonInAlbumActivity(true)
                .setSelectedImages(allPhotos)
                .startAlbumWithActivityResultCallback(photoLauncher)
        
    }

    fun albumPhoto() {
//        Album.camera(this)
//            .image()
//            .filePath("")
//            .onResult(object : Action<String> {
//                override fun onAction(result: String) {
//                    Log.d("#####", result.toString())
//                    iv_photo.setImageURI(result.toUri())
//
//                    val finalBitmap =
//                        BitmapFactory.decodeFile(result)
//                    iv_photo.setImageBitmap(finalBitmap)
//
//                }
//
//            }).onCancel(object : Action<String> {
//                override fun onAction(result: String) {
//                }
//
//            }).start()
    }

    val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                allPhotos =
                    it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedPhoto = allPhotos.get(0)
                iv_photo.setImageURI(pickedPhoto)
            }
        }
}