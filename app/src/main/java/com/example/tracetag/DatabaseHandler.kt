package com.example.tracetag


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase"
        private const val TABLE_USERS = "UserTable"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_LOCATION = "location"
        private const val KEY_MOBILE_NUMBER = "mobile_number"
        private const val KEY_FACEBOOK = "facebook"
        private const val KEY_EMAIL = "email"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        // Creating the Users table
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" + "$KEY_ID INTEGER PRIMARY KEY," + "$KEY_NAME TEXT,"
                + "$KEY_USERNAME TEXT," + "$KEY_PASSWORD TEXT," + "$KEY_LOCATION TEXT," + "$KEY_MOBILE_NUMBER TEXT,"
                + "$KEY_FACEBOOK TEXT," + "$KEY_EMAIL TEXT" + ")")


        db?.execSQL(CREATE_USERS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Drop older table if it exists and create a new one
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }


    // Function to add a new user to the database
    fun addUser(user: UserModelClass): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, user.name)
        contentValues.put(KEY_USERNAME, user.username)
        contentValues.put(KEY_PASSWORD, user.password)
        contentValues.put(KEY_LOCATION, user.location)
        contentValues.put(KEY_MOBILE_NUMBER, user.mobileNumber)
        contentValues.put(KEY_FACEBOOK, user.facebook)
        contentValues.put(KEY_EMAIL, user.email)


        // Inserting Row
        val success = db.insert(TABLE_USERS, null, contentValues)
        db.close() // Closing database connection


        // Store the user ID in SharedPreferences
        if (success != -1L) {
            saveUserIdToSharedPreferences(success)
        }


        return success
    }


    // Function to authenticate a user during login
    fun loginUser(username: String, password: String): UserModelClass? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_USERS WHERE $KEY_USERNAME = ? AND $KEY_PASSWORD = ?"
        val cursor: Cursor?


        try {
            cursor = db.rawQuery(selectQuery, arrayOf(username, password))
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return null
        }


        var user: UserModelClass? = null


        if (cursor.moveToFirst()) {
            // Ensure the column indices are correct
            val userIdIndex = cursor.getColumnIndex(KEY_ID)
            val nameIndex = cursor.getColumnIndex(KEY_NAME)
            val usernameIndex = cursor.getColumnIndex(KEY_USERNAME)
            val passwordIndex = cursor.getColumnIndex(KEY_PASSWORD)
            val locationIndex = cursor.getColumnIndex(KEY_LOCATION)
            val mobileNumberIndex = cursor.getColumnIndex(KEY_MOBILE_NUMBER)
            val facebookIndex = cursor.getColumnIndex(KEY_FACEBOOK)
            val emailIndex = cursor.getColumnIndex(KEY_EMAIL)


            if (userIdIndex >= 0 && nameIndex >= 0 && usernameIndex >= 0 && passwordIndex >= 0 &&
                locationIndex >= 0 && mobileNumberIndex >= 0 && facebookIndex >= 0 && emailIndex >= 0
            ) {
                user = UserModelClass(
                    userId = cursor.getInt(userIdIndex),
                    name = cursor.getString(nameIndex),
                    username = cursor.getString(usernameIndex),
                    password = cursor.getString(passwordIndex),
                    location = cursor.getString(locationIndex),
                    mobileNumber = cursor.getString(mobileNumberIndex),
                    facebook = cursor.getString(facebookIndex),
                    email = cursor.getString(emailIndex)
                )


                // Save the user ID to SharedPreferences
                saveUserIdToSharedPreferences(user.userId.toLong())
            }
        }
        cursor.close()
        db.close()
        return user
    }


    // Function to update a user in the database
    fun updateUser(user: UserModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_PASSWORD, user.password)


        // Updating Row
        val success = db.update(TABLE_USERS, contentValues, "$KEY_ID=?", arrayOf(user.userId.toString()))
        db.close() // Closing database connection
        return success
    }


    private fun saveUserIdToSharedPreferences(userId: Long) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("user_id", userId)
        editor.apply()
    }


    fun getUserById(userId: Int): UserModelClass? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_USERS WHERE $KEY_ID = ?"
        val cursor: Cursor?


        try {
            cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return null
        }


        var user: UserModelClass? = null


        if (cursor.moveToFirst()) {
            // Ensure the column indices are correct
            val nameIndex = cursor.getColumnIndex(KEY_NAME)
            val usernameIndex = cursor.getColumnIndex(KEY_USERNAME)
            val locationIndex = cursor.getColumnIndex(KEY_LOCATION)
            val mobileNumberIndex = cursor.getColumnIndex(KEY_MOBILE_NUMBER)
            val facebookIndex = cursor.getColumnIndex(KEY_FACEBOOK)
            val emailIndex = cursor.getColumnIndex(KEY_EMAIL)


            if (nameIndex >= 0 && usernameIndex >= 0 && locationIndex >= 0 &&
                mobileNumberIndex >= 0 && facebookIndex >= 0 && emailIndex >= 0
            ) {
                user = UserModelClass(
                    userId = userId,
                    name = cursor.getString(nameIndex),
                    username = cursor.getString(usernameIndex),
                    password = "", // You might want to handle this differently
                    location = cursor.getString(locationIndex),
                    mobileNumber = cursor.getString(mobileNumberIndex),
                    facebook = cursor.getString(facebookIndex),
                    email = cursor.getString(emailIndex)
                )
            }
        }
        cursor.close()
        db.close()
        return user
    }
}
