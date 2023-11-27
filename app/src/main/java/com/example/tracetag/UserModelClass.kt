package com.example.tracetag


data class UserModelClass(
    val userId: Int = 0,
    val name: String = "default",
    val username: String = "default",
    var password: String = "12345678",
    val location: String = "default",
    val mobileNumber: String = "09999999999",
    val facebook: String = "default",
    val email: String = "default")
