package com.example.flipshelf

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.flipshelf.Activity.LoginActivity
import com.example.flipshelf.Activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class Splash_Screen : AppCompatActivity() {
    var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser!=null){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        },1000)
}}