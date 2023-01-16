package com.example.bintask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bintask.R
import com.example.bintask.database.data.RequestModel

class RequestAdapter : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    private var requests:List<RequestModel> = emptyList()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRequestBIN: TextView
        val tvRequestDate: TextView

        init {
            // Define click listener for the ViewHolder's View
            tvRequestBIN = view.findViewById(R.id.tvRequestBIN)
            tvRequestDate = view.findViewById(R.id.tvRequestDate)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_request, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvRequestBIN.text = requests[position].BIN
        viewHolder.tvRequestDate.text = requests[position].requestDateTime
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = requests.size

    fun setData(newRequests: List<RequestModel>) {
        requests = newRequests
        notifyDataSetChanged()
    }

}