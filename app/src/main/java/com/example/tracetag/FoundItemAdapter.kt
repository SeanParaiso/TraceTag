// FoundItemAdapter.kt (within the same file)
package com.example.tracetag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

interface ItemClickListener {
    fun onItemClick(foundItem: FoundItem)
}


class FoundItemAdapter(
    private val context: Context,
    private val foundItems: MutableList<FoundItem>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<FoundItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleImage: ShapeableImageView = itemView.findViewById(R.id.titleImage)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvFound: TextView = itemView.findViewById(R.id.tvFound)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.found_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foundItem = foundItems[position]

        // Populate the views with data from the FoundItem object
        // You need to implement the logic for loading the image, e.g., using Glide
        holder.titleImage.setImageResource(R.drawable.sample) // Replace with actual logic

        holder.tvName.text = foundItem.itemName
        holder.tvLocation.text = foundItem.location
        holder.tvFound.text = foundItem.dateFound

        // Set a click listener for the item view
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(foundItem)
        }
    }

    override fun getItemCount(): Int {
        return foundItems.size
    }

    fun updateData(newData: List<FoundItem>) {
        foundItems.clear()
        foundItems.addAll(newData)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val deletedItem = foundItems[position]
        foundItems.removeAt(position)
        notifyItemRemoved(position)

        // Get the ID from the deleted item
        val deletedItemId = deletedItem.id

        // Assuming you have an ID field in FoundItem
        // Pass the databaseHelper instance to the adapter when creating it
        val databaseHelper = DatabaseHelper(context)

        // Call the deleteFoundItem function from your databaseHelper
        val deletedRows = databaseHelper.deleteFoundItem(deletedItemId)

        if (deletedRows > 0) {
            // Successfully deleted from the database
            Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            // Failed to delete from the database
            Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show()
        }
    }
}
