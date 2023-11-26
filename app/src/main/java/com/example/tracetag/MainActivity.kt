package com.example.tracetag

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val addbtn = findViewById<ImageButton>(R.id.addbtn)
        addbtn.setOnClickListener {
            val message = ""
            showAddbtn(message)
        }
    }


    fun showAddbtn(message: String){
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.clicking_add_button)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val addLostBtn = dialog.findViewById<ImageButton>(R.id.btnAddLost)
        val addFoundBtn = dialog.findViewById<ImageButton>(R.id.btnAddFound)

        addLostBtn.setOnClickListener {
            val i = Intent(this, AddLostItem::class.java)
            startActivity(i)
        }
        addFoundBtn.setOnClickListener {
            val i = Intent(this, AddFoundItem::class.java)
            startActivity(i)
        }
    }



}