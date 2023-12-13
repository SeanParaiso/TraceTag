package com.example.tracetag

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddFoundItem : AppCompatActivity() {

    private lateinit var etItemName: EditText
    private lateinit var etLocFound: EditText
    private lateinit var etDescFound: EditText
    private lateinit var etDateFound: EditText
    private lateinit var etMobile: EditText
    private lateinit var etCategoryFound: EditText
    private lateinit var etEmail: EditText

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_found_item)



        etItemName = findViewById(R.id.etItemName)
        etLocFound = findViewById(R.id.etLocFound)
        etDescFound = findViewById(R.id.etDescFound)
        etDateFound = findViewById(R.id.etDateFound)
        etMobile = findViewById(R.id.etMobile)
        etCategoryFound = findViewById(R.id.etCategoryFound)
        etEmail = findViewById(R.id.etEmail)

        databaseHelper = DatabaseHelper(this)

        val btnSubmit = findViewById<Button>(R.id.btnFound)
        btnSubmit.setOnClickListener {
            saveFoundItem()
        }

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnUpload = findViewById<Button>(R.id.uploadfound)
        btnUpload.setOnClickListener {
            // Add functionality for uploading photos if needed
        }
    }

    private fun saveFoundItem() {
        val itemName = etItemName.text.toString()
        val location = etLocFound.text.toString()
        val description = etDescFound.text.toString()
        val dateFound = etDateFound.text.toString()
        val mobileNumber = etMobile.text.toString()
        val facebookProfile = etCategoryFound.text.toString()
        val emailAddress = etEmail.text.toString()

        val foundItem = FoundItem(1,itemName, location, description, dateFound, mobileNumber, facebookProfile, emailAddress)

        val rowId = databaseHelper.insertFoundItem(foundItem)

        if (rowId != -1L) {
            // Item saved successfully
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()

            // Navigate back to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Error saving item
            Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
        }
    }
}
