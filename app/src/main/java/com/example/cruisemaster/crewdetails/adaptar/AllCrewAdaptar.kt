package com.example.cruisemaster.crewdetails.adaptar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cruisemaster.R
import com.example.cruisemaster.crewdetails.model.User
import com.example.cruisemaster.databinding.UserViewBinding


class AllCrewAdaptar(val guests: ArrayList<User>) : RecyclerView.Adapter<AllCrewAdaptar.GuestNoViewHolder>() {

    interface OnCreawDetailsListener {
        fun oncreawdetailsCLick(user: User, position: Int, allCrewAdaptar: AllCrewAdaptar)
    }

    var onCreawDetailsListener: OnCreawDetailsListener? = null

    inner class GuestNoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: UserViewBinding = UserViewBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                onCreawDetailsListener?.oncreawdetailsCLick(
                    guests[adapterPosition],
                    adapterPosition,
                    this@AllCrewAdaptar
                )
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("Size", guests.size.toString())
        return guests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestNoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crew_view, parent, false)
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