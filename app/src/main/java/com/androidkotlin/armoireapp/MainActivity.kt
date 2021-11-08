package com.androidkotlin.armoireapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
        play()
    }
    fun play(){
            val activity = Intent(this,LoginActivity::class.java)
            startActivity(activity)
    }
}