package com.pragati.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        Creates a Splash Screen for 1 Sec
        Handler().postDelayed({
            val startAct = Intent(this@SplashActivity,
                MainActivity::class.java)
            startActivity(startAct)
            finish()
        },1000)
    }
}
