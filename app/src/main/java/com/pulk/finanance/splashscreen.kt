package com.pulk.finanance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class splashscreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val time: Long =2000

        Handler().postDelayed(Runnable {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, time)

    }
}