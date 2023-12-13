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
    private lateinit var dbHandler: DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)


        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        dbHandler = DatabaseHandler(this)


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


        // Retrieve user_id from SharedPreferences
        val userId = sharedPreferences.getLong("user_id", 0)


        // Retrieve user details from the database using user_id
        val user = dbHandler.getUserById(userId.toInt())


        if (user != null) {
            // Set user details to Text Views
            val nameTextView = findViewById<TextView>(R.id.tvName)
            val userNameTextView = findViewById<TextView>(R.id.tvUsername)
            val locationTextView = findViewById<TextView>(R.id.tvLoc)
            val conNumTextView = findViewById<TextView>(R.id.tvNum)
            val fbProfTextView = findViewById<TextView>(R.id.tvFb)
            val emailTextView = findViewById<TextView>(R.id.tvEmail)


            nameTextView.text = user.name
            userNameTextView.text = user.username
            locationTextView.text = user.location
            conNumTextView.text = user.mobileNumber
            fbProfTextView.text = user.facebook
            emailTextView.text = user.email
        }

val logout = findViewById<ImageButton>(R.id.btnLogout)
        logout.setOnClickListener{
            val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            toLogin()
        }
    }
    fun toLogin(){
        val loginIntent = Intent(this, MainActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
}