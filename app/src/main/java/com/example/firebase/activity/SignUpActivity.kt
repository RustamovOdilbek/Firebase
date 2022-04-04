package com.example.firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebase.R.id.tv_signin
import com.example.firebase.activity.BaseActivity
import com.example.firebase.manager.AuthHandler
import com.example.firebase.manager.AuthManager
import com.example.firebase.utls.Extensions.toast
import java.lang.Exception

class SignUpActivity : BaseActivity() {
    lateinit var context: Context
    private lateinit var et_fullname:TextView
    private lateinit var et_email:TextView
    private lateinit var et_password:TextView
    private lateinit var et_c_password:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        context = this

        initViews()
    }
    fun initViews(){
        et_fullname = findViewById(R.id.et_mame)
        et_email = findViewById(R.id.et_email_up)
        et_password = findViewById(R.id.et_password_1)
        et_c_password = findViewById(R.id.et_password_2)
        var btn_signup = findViewById<Button>(R.id.btn_signup)
        val tv_signup = findViewById<TextView>(tv_signin)
        btn_signup.setOnClickListener(View.OnClickListener {
           firebaseSignUp(et_email.text.toString(), et_password.text.toString())
        })
        tv_signup.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun firebaseSignUp(email: String, password: String) {
        showLoading(this)
       AuthManager.signUp(email, password, object : AuthHandler{
           override fun onSuccss() {
               toast("Signed up successfully")
               dismissLoading()
               callMainActivity(context)
           }

           override fun onError(exception: Exception) {
               toast("Sign up failed")
           }

       })
    }
}