package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.firebase.R
import com.example.firebase.manager.AuthManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var signout  = findViewById<Button>(R.id.tv_signOut)

        signout.setOnClickListener {
            AuthManager.signOut()
        }
    }
}