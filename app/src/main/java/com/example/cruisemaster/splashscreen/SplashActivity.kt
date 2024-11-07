package com.example.cruisemaster.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.cruisemaster.MainActivity
import com.example.cruisemaster.R
import com.example.cruisemaster.constant.SHIP_ID
import com.example.cruisemaster.login.activity.LoginActivity
import com.example.cruisemaster.shiplist.fragment.ShipListFragment
import com.example.cruisemaster.viewdetails.fragment.ViewDetailsFragment

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("SplashActivity", "FragmentCounts ${supportFragmentManager.backStackEntryCount}")
    }
}