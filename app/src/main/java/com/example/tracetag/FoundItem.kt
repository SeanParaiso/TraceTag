package com.example.tracetag

import java.io.Serializable


data class FoundItem(
    val id: Int,
    val itemName: String,
    val location: String,
    val description: String,
    val dateFound: String,
    val mobileNumber: String,
    val facebookProfile: String,
    val emailAddress: String
) : Serializable


