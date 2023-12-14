
package com.example.tracetag

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.widget.Toast


class ItemDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item)

        val btnAdd = findViewById<ImageButton>(R.id.addbtn)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddFoundItem::class.java)
            startActivity(intent)
        }

        val btnAbout = findViewById<ImageButton>(R.id.info)
        btnAbout.setOnClickListener{
            val intent = Intent(this, aboutUs::class.java )
            startActivity(intent)
        }

        val btnProfile = findViewById<ImageButton>(R.id.profilebtn)
        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val btnHome = findViewById<ImageButton>(R.id.homebtn)
        btnHome.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }


        // Retrieve item details from intent
        val foundItem = intent.getSerializableExtra("foundItem") as FoundItem

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val itemId = foundItem.id

        val foundNameTextView = findViewById<TextView>(R.id.foundName)
        val foundLocationTextView = findViewById<TextView>(R.id.foundLocation)
        val foundDescriptionTextView = findViewById<TextView>(R.id.foundDescription)
        val foundDateTextView = findViewById<TextView>(R.id.foundDate)
        val foundNumberTextView = findViewById<TextView>(R.id.FoundNumber)
        val foundFacebookTextView = findViewById<TextView>(R.id.foundFacebook)
        val foundEmailTextView = findViewById<TextView>(R.id.foundEmail)


        foundNameTextView.text = foundItem.itemName
        foundLocationTextView.text = foundItem.location
        foundDescriptionTextView.text = foundItem.description
        foundDateTextView.text = foundItem.dateFound
        foundNumberTextView.text = foundItem.mobileNumber
        foundFacebookTextView.text = foundItem.facebookProfile
        foundEmailTextView.text = foundItem.emailAddress

        val btnDelete = findViewById<ImageButton>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            deleteFoundItem(foundItem.id)
        }
    }
    private fun deleteFoundItem(itemId: Int) {
        val databaseHelper = DatabaseHelper(this)
        val rowsAffected = databaseHelper.deleteFoundItem(itemId)

        if (rowsAffected > 0) {
            // Item deleted successfully
            Toast.makeText(this, "Item deleted successfully.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Item deletion is unsuccessful. Please try again.", Toast.LENGTH_SHORT).show()
        }
        onDeleteButtonClick()
    }

    private fun onDeleteButtonClick() {
        setResult(Activity.RESULT_OK)
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


}
