package com.example.firebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebase.activity.BaseActivity
import com.example.firebase.manager.AuthHandler
import com.example.firebase.manager.AuthManager
import com.example.firebase.utls.Extensions.toast
import java.lang.Exception

class SignInActivity : BaseActivity() {
    lateinit var context: Context
    private lateinit var et_email:TextView
    private lateinit var et_password:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        context = this

        initViews()
    }
    fun initViews(){
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        var btn_signin = findViewById<Button>(R.id.b_signin)
        var tv_signup = findViewById<TextView>(R.id.tv_signup)
        btn_signin.setOnClickListener(View.OnClickListener {
           firebaseSignIn(et_email.text.toString(), et_password.text.toString())
        })
        tv_signup.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })
    }

    private fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.signIn(email, password, object : AuthHandler{
            override fun onSuccss() {
                toast("Signed in successfully")
                dismissLoading()
                callMainActivity(context)
            }

            override fun onError(exception: Exception) {
                toast("Signed is failed")
                Log.d("@@@@", exception.toString())
            }

        })
    }
}