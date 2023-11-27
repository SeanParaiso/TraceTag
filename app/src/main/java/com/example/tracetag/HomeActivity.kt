package com.example.tracetag

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val addbtn = findViewById<ImageButton>(R.id.addbtn)
        addbtn.setOnClickListener {
            val i = Intent(this, AddFoundItem::class.java)
            startActivity(i)
        }

        val btnProfile = findViewById<ImageButton>(R.id.profilebtn)
        btnProfile.setOnClickListener {
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }

    }
}