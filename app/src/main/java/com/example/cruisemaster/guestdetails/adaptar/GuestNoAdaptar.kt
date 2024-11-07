package com.example.cruisemaster.guestdetails.adaptar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.cruisemaster.R

import com.example.cruisemaster.databinding.UserViewBinding
import com.example.cruisemaster.guestdetails.model.User4

class GuestNoAdaptar(val guests: ArrayList<User4>) : RecyclerView.Adapter<GuestNoAdaptar.GuestNoViewHolder>() {

    interface OnCreawDetailsListener {
        fun oncreawdetailsCLick(user4: User4, position: Int, guestNoAdapter: GuestNoAdaptar)
    }

    var onCreawDetailsListener: OnCreawDetailsListener? = null

    inner class GuestNoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: UserViewBinding = UserViewBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                onCreawDetailsListener?.oncreawdetailsCLick(
                    guests[adapterPosition],
                    adapterPosition,
                    this@GuestNoAdaptar
                )
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("Size", guests.size.toString())
        return guests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestNoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.guest_view, parent, false)
        return GuestNoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestNoViewHolder, position: Int) {
        val user4 = guests[position]
        val fullName = "${user4.firstName} ${user4.lastName}"
        holder.binding.text1.text = fullName
        holder.binding.text2.text = user4.isCheckedIn
        holder.binding.text3.text = user4.isOnboard
        holder.binding.text4.text = user4.cabinNo.toString()
    }
}