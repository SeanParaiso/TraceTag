package com.example.tracetag

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FoundItemsDB"
        const val TABLE_NAME = "found_items"

        // Column names
        const val COLUMN_ID = "id"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE_FOUND = "date_found"
        const val COLUMN_MOBILE_NUMBER = "mobile_number"
        const val COLUMN_FACEBOOK_PROFILE = "facebook_profile"
        const val COLUMN_EMAIL_ADDRESS = "email_address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_NAME TEXT,
                $COLUMN_LOCATION TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_DATE_FOUND TEXT,
                $COLUMN_MOBILE_NUMBER TEXT,
                $COLUMN_FACEBOOK_PROFILE TEXT,
                $COLUMN_EMAIL_ADDRESS TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertFoundItem(foundItem: FoundItem): Long {
        val values = ContentValues().apply {
            put(COLUMN_ITEM_NAME, foundItem.itemName)
            put(COLUMN_LOCATION, foundItem.location)
            put(COLUMN_DESCRIPTION, foundItem.description)
            put(COLUMN_DATE_FOUND, foundItem.dateFound)
            put(COLUMN_MOBILE_NUMBER, foundItem.mobileNumber)
            put(COLUMN_FACEBOOK_PROFILE, foundItem.facebookProfile)
            put(COLUMN_EMAIL_ADDRESS, foundItem.emailAddress)

        }

        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllFoundItems(): List<FoundItem> {
        val foundItems = mutableListOf<FoundItem>()
        val db = this.readableDatabase

        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_ITEM_NAME,
            COLUMN_LOCATION,
            COLUMN_DESCRIPTION,
            COLUMN_DATE_FOUND,
            COLUMN_MOBILE_NUMBER,
            COLUMN_FACEBOOK_PROFILE,
            COLUMN_EMAIL_ADDRESS
        )

        val cursor = db.query(TABLE_NAME, projection, null, null, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val itemId = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val itemName = it.getString(it.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                val location = it.getString(it.getColumnIndexOrThrow(COLUMN_LOCATION))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val dateFound = it.getString(it.getColumnIndexOrThrow(COLUMN_DATE_FOUND))
                val mobileNumber = it.getString(it.getColumnIndexOrThrow(COLUMN_MOBILE_NUMBER))
                val facebookProfile = it.getString(it.getColumnIndexOrThrow(COLUMN_FACEBOOK_PROFILE))
                val emailAddress = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_ADDRESS))

                val foundItem = FoundItem(
                    itemId,  // Pass the item ID to FoundItem
                    itemName,
                    location,
                    description,
                    dateFound,
                    mobileNumber,
                    facebookProfile,
                    emailAddress
                )
                foundItems.add(foundItem)
            }
        }

        return foundItems
    }
    fun deleteFoundItem(itemId: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(itemId.toString()))
        db.close()
        return success
    }

    fun searchFoundItems(query: String): List<FoundItem> {
        val foundItems = mutableListOf<FoundItem>()
        val db = this.readableDatabase

        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_ITEM_NAME,
            COLUMN_LOCATION,
            COLUMN_DESCRIPTION,
            COLUMN_DATE_FOUND,
            COLUMN_MOBILE_NUMBER,
            COLUMN_FACEBOOK_PROFILE,
            COLUMN_EMAIL_ADDRESS
        )

        val selection = "$COLUMN_ITEM_NAME LIKE ?"
        val selectionArgs = arrayOf("%$query%")

        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val itemId = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val itemName = it.getString(it.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                val location = it.getString(it.getColumnIndexOrThrow(COLUMN_LOCATION))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val dateFound = it.getString(it.getColumnIndexOrThrow(COLUMN_DATE_FOUND))
                val mobileNumber = it.getString(it.getColumnIndexOrThrow(COLUMN_MOBILE_NUMBER))
                val facebookProfile = it.getString(it.getColumnIndexOrThrow(COLUMN_FACEBOOK_PROFILE))
                val emailAddress = it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL_ADDRESS))

                val foundItem = FoundItem(
                    itemId,  // Pass the item ID to FoundItem
                    itemName,
                    location,
                    description,
                    dateFound,
                    mobileNumber,
                    facebookProfile,
                    emailAddress
                )
                foundItems.add(foundItem)
            }
        }

        return foundItems
    }


}
