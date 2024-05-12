package com.example.studybuddy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.studybuddy.databinding.ActivityLaunchScreenBinding

class LaunchScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Delay for 2 seconds before navigating to MainActivity
        Handler().postDelayed({
            startActivity(Intent(this@LaunchScreenActivity, MainActivity::class.java))
            finish() // Finish the current activity
        }, 2000)
    }
}
