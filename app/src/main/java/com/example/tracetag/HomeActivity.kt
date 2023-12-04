// HomeActivity.kt
package com.example.tracetag

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foundItemAdapter: FoundItemAdapter
    private lateinit var databaseHelper: DatabaseHelper

    companion object {
        private const val ITEM_DETAILS_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val btnAdd = findViewById<ImageButton>(R.id.addbtn)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddFoundItem::class.java)
            startActivity(intent)
        }

        val btnAbout = findViewById<ImageButton>(R.id.btnabout)
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


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize databaseHelper before using it
        databaseHelper = DatabaseHelper(this)

        // Initialize the adapter with an empty list and set the item click listener
        foundItemAdapter = FoundItemAdapter(this, mutableListOf(), this)
        recyclerView.adapter = foundItemAdapter

        // Load found items
        loadFoundItems()
    }

    private fun loadFoundItems() {
        // Retrieve data from the database using the DatabaseHelper
        val foundItems = databaseHelper.getAllFoundItems()

        // Update the adapter with the retrieved data
        foundItemAdapter.updateData(foundItems)
    }

    override fun onItemClick(foundItem: FoundItem) {
        // Display details in ItemDetailsActivity
        val intent = Intent(this, ItemDetailsActivity::class.java).apply {
            putExtra("foundItem", foundItem)
        }
        startActivityForResult(intent, ITEM_DETAILS_REQUEST_CODE)
    }

    fun deleteFoundItem(itemId: Int): Int {
        val db = databaseHelper.writableDatabase
        val success = db.delete(DatabaseHelper.TABLE_NAME, "${DatabaseHelper.COLUMN_ID}=?", arrayOf(itemId.toString()))
        db.close()
        return success
    }
}
