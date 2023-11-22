package com.example.tracetag

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SignupActivity : AppCompatActivity() {

    private val sharedPrefFile = "user_prefs"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Initialize your EditText elements
        val etName = findViewById<EditText>(R.id.etName)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etMobileNumber = findViewById<EditText>(R.id.etMobileNumber)
        val etFacebook = findViewById<EditText>(R.id.etFacebook)
        val etEmail = findViewById<EditText>(R.id.etEmail)

        val btnSignup = findViewById<Button>(R.id.btnSignup)

        // Create an instance of the DatabaseHandler
        val dbHandler = DatabaseHandler(this)

        btnSignup.setOnClickListener {
            // Retrieve values from EditText elements
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
                userId = 0,
                name = name,
                username = username,
                password = password,
                location = location,
                mobileNumber = mobileNumber,
                facebook = facebook,
                email = email
            )

            // Add the user details to the database
            val result = dbHandler.addUser(user)

            // Check the result of the database insertion
            if (result > -1) {
                // Database insertion successful
                // You can add additional logic or navigate to another screen if needed
                Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // Database insertion failed
                // You can display an error message or handle it accordingly
                Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
