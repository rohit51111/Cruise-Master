package com.example.cruisemaster.shiplist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.cruisemaster.R
import com.example.cruisemaster.shiplist.model.Ship


class ShipListAdapter(
    private val context: Context,
    private val ships: List<Ship>,
    @LayoutRes private val resource: Int
) : ArrayAdapter<Ship>(context, resource, ships) {

    private var selectedPosition: Int = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val shipNameTextView = view.findViewById<TextView>(R.id.tvShipName)
        val dropDownIcon = view.findViewById<ImageView>(R.id.spinner_icon)
        val ship = ships[position]

        shipNameTextView.text = ship.shipName

        // Show the drop-down icon only on the selected item
//        dropDownIcon.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_spinner_dropdown_item, parent, false)
        val ship = getItem(position)
        val textView = view.findViewById<TextView>(R.id.spinner_dropdown_text)
        textView.text = ship?.shipName
        return view
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }
}