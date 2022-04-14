package com.example.firebase.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.example.firebase.model.Post

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is PostViewHolder){
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body
            val ll_post = holder.ll_post
            val iv_update = holder.iv_update
            val iv_delete = holder.iv_delete
            val iv_photo = holder.iv_photo

            iv_delete.setOnClickListener {
                activity.apiDeletePost(item)
            }


            tv_title.text = item.title!!.toUpperCase()
            tv_body.text = item.body
            if (item.img.length > 0) {
                Glide.with(iv_photo.context).load(item.img).into(iv_photo)
                Log.d("@@@", item.img)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title: TextView
        val tv_body: TextView
        val ll_post: LinearLayout
        val iv_update: ImageView
        val iv_delete: ImageView
        val iv_photo: ImageView

        init {
            tv_title = view.findViewById(R.id.tv_title)
            tv_body = view.findViewById(R.id.tv_body)
            ll_post = view.findViewById(R.id.ll_post)
            iv_update = view.findViewById(R.id.iv_update)
            iv_delete = view.findViewById(R.id.iv_delete)
            iv_photo = view.findViewById(R.id.iv_photo)
        }
    }
    init {
        this.activity = activity
        this.items = items
    }
}