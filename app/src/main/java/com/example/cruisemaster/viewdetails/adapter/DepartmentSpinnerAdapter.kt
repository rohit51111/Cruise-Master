package com.example.cruisemaster.viewdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.cruisemaster.R


class DepartmentSpinnerAdapter(
    context: Context,
    @LayoutRes private val resource: Int,
    private val departments: List<String>
) : ArrayAdapter<String>(context, resource, departments) {

    private var selectedPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val crewDepartmentTextView = view.findViewById<TextView>(R.id.tvCrewDepartment)
        val dropDownIcon = view.findViewById<ImageView>(R.id.spinner_icon)

        val department = departments[position]
        crewDepartmentTextView.text = department

        // Show the drop-down icon only if this is the selected item
//        dropDownIcon.visibility = if (position == selectedPosition) {
//            View.VISIBLE
//        } else {
//            View.GONE
//        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_spinner_dropdown_item, parent, false)
        val crewDepartmentTextView = view.findViewById<TextView>(R.id.spinner_dropdown_text)
        val dropDownIcon = view.findViewById<ImageView>(R.id.spinner_icon)

        val department = departments[position]
        crewDepartmentTextView.text = department

        return view
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged() // Notify adapter to refresh the views
    }
}