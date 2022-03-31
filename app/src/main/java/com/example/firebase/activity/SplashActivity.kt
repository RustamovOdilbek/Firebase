package com.example.firebase

import android.os.Bundle
import android.os.CountDownTimer
import com.example.firebase.activity.BaseActivity
import com.example.firebase.manager.AuthManager

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViews()
    }
    fun initViews(){
        countDownTimer()
    }
    fun countDownTimer(){
        object : CountDownTimer(2000, 1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
               if (AuthManager.isSignedIn()){
                   callMainActivity(this@SplashActivity)
               }else{
                   callSignInActivity(this@SplashActivity)
               }
            }
        }.start()
    }
}