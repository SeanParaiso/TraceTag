package com.example.tracetag

import java.io.Serializable

// FoundItem.kt
data class FoundItem(
    val id: Int, // Ensure you have this line
    val itemName: String,
    val location: String,
    val description: String,
    val dateFound: String,
    val mobileNumber: String,
    val facebookProfile: String,
    val emailAddress: String
) : Serializable


