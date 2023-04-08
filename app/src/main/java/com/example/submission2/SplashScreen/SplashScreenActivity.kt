package com.example.submission2.SplashScreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission2.R
import android.os.Handler
import android.content.Intent
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.example.submission2.Main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this@SplashScreenActivity,
//                MainActivity::class.java))
//            finish()
//        },2000L)

        lifecycleScope.launch {
            delay(2000L)
            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}