package com.example.githubuser.UI.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.githubuser.R
//import com.example.githubuser.UI.MainActivity
import com.example.githubuser.UI.SearchActivity

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent= Intent(this,SearchActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}