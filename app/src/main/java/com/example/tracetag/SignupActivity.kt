package com.example.tracetag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SignupActivity : AppCompatActivity() {

    private val sharedPrefFile = "user_prefs"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val etName = findViewById<EditText>(R.id.etName)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etMobileNumber = findViewById<EditText>(R.id.etMobileNumber)
        val etFacebook = findViewById<EditText>(R.id.etFacebook)
        val etEmail = findViewById<EditText>(R.id.etEmail)

        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val dbHandler = DatabaseHandler(this)

        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val location = etLocation.text.toString()
            val mobileNumber = etMobileNumber.text.toString()
            val facebook = etFacebook.text.toString()
            val email = etEmail.text.toString()

            // Check if any field is empty
            if (name.isEmpty() || username.isEmpty() || password.isEmpty() ||
                location.isEmpty() || mobileNumber.isEmpty() || facebook.isEmpty() || email.isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an instance of UserModelClass with the retrieved values
            val user = UserModelClass(
                userId = 0, name = name, username = username, password = password,
                location = location, mobileNumber = mobileNumber, facebook = facebook, email = email)

            val result = dbHandler.addUser(user)

            if (result > -1) {
                Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                saveUserDetailsToSharedPreferences(username, name, password, location, mobileNumber, facebook, email)
                finish()
            } else {
                Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




    }
    private fun saveUserDetailsToSharedPreferences(
        username: String,
        name: String,
        password: String,
        location: String,
        mobileNumber: String,
        facebook: String,
        email: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("name", name)
        editor.putString("password", password)
        editor.putString("location", location)
        editor.putString("mobileNumber", mobileNumber)
        editor.putString("facebook", facebook)
        editor.putString("email", email)
        editor.apply()
    }
}
