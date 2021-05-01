package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var adapter: LoginAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        val tabLayout: TabLayout = findViewById(R.id.login_tab_layout)
        val viewPager: ViewPager = findViewById(R.id.login_view_pager)
        val fb: FloatingActionButton = findViewById(R.id.login_fab_fb)
        val google: FloatingActionButton = findViewById(R.id.login_fab_google)
        val twitter: FloatingActionButton = findViewById(R.id.login_fab_twitter)
        val skipButton: TextView = findViewById(R.id.login_skip_button)

        adapter = LoginAdapter(supportFragmentManager, this, 2)
        viewPager.adapter = adapter

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        fb.translationY = 300F
        google.translationY = 300F
        twitter.translationY = 300F
        tabLayout.translationY = 300F

        fb.alpha = 0F
        google.alpha = 0F
        twitter.alpha = 0F
        tabLayout.alpha = 0F

        fb.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
        google.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(600).start()
        twitter.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(800).start()
        tabLayout.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(1000).start()

        skipButton.setOnClickListener {
            updateUI("GUEST")
        }

    }

    override fun onStart() {
        super.onStart()

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        //Check if already logged in
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    private fun updateUI(text: String) {
        if (text == "GUEST") {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}