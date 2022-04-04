package com.example.firebase.manager

import com.example.firebase.model.Post


interface DatabaseHandler {
    fun onSuccess(post: Post? = null, posts: ArrayList<Post> = ArrayList())
    fun onError()
}