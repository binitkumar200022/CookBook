package com.example.cookbook

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        //Animations
        val topAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val appLogo : ImageView = findViewById(R.id.landing_app_logo)
        val appName : TextView = findViewById(R.id.landing_app_name)
        val slogan : TextView = findViewById(R.id.landing_app_slogan)
        appLogo.animation = topAnim
        appName.animation = bottomAnim
        slogan.animation = bottomAnim
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())
    }

    companion object {
        //Variables
        private const val SPLASH_SCREEN = 3000
    }
}