package com.example.tracetag

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnUpdatePassword: Button

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnUpdatePassword = findViewById(R.id.btnUpdate)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        dbHandler = DatabaseHandler(this)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        btnUpdatePassword.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {
        val oldPassword = etOldPassword.text.toString()
        val newPassword = etNewPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        val userId = sharedPreferences.getLong("user_id", 0L)

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if new password matches confirm password
        if (newPassword != confirmPassword) {
            Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the stored user ID is not null
        if (userId != 0L) {
            // Update the password in the database
            val updateResult = dbHandler.updateUser(userId.toInt(), oldPassword, newPassword)

            if (updateResult > 0) {
                // Update the password in SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("password", newPassword)
                editor.apply()

                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Incorrect current password or failed to update password in the database", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }
    }



}
