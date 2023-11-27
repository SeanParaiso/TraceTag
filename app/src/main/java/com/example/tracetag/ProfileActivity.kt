package com.example.tracetag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private val sharedPrefFile = "user_prefs"
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnChangePass = findViewById<Button>(R.id.changepass)
        val btnHome = findViewById<ImageButton>(R.id.homebtn)
        val addbtn = findViewById<ImageButton>(R.id.addbtn)

        addbtn.setOnClickListener {
            val i = Intent(this, AddFoundItem::class.java)
            startActivity(i)
        }

        btnHome.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnChangePass.setOnClickListener{
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Retrieve user details from SharedPreferences
        val username = sharedPreferences.getString("username", "")
        val name = sharedPreferences.getString("name", "")
        val location = sharedPreferences.getString("location", "")
        val mobileNumber = sharedPreferences.getString("mobileNumber", "")
        val facebook = sharedPreferences.getString("facebook", "")
        val email = sharedPreferences.getString("email", "")

        // Set user details to Text Views
        val nameTextView = findViewById<TextView>(R.id.tvName)
        val locationTextView = findViewById<TextView>(R.id.tvLoc)
        val conNumTextView = findViewById<TextView>(R.id.tvNum)
        val fbProfTextView = findViewById<TextView>(R.id.tvFb)
        val emailTextView = findViewById<TextView>(R.id.tvEmail)

        nameTextView.text = "Name: $name"
        locationTextView.text = "Location: $location"
        conNumTextView.text = "Contact Number: $mobileNumber"
        fbProfTextView.text = "Facebook Profile: $facebook"
        emailTextView.text = "Email: $email"

    }
}