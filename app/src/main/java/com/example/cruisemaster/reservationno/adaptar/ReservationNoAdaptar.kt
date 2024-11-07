package com.example.cruisemaster.reservationno.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.cruisemaster.R

import com.example.cruisemaster.databinding.UserViewBinding
import com.example.cruisemaster.reservationno.model.User3

class ReservationNoAdaptar(private var guests: ArrayList<User3>) :
    RecyclerView.Adapter<ReservationNoAdaptar.ReservationNoViewModel>() {

    interface OnReservationDetailsListener {
        fun onreservationdetailsCLick(user3: User3, position: Int, reservationNoAdaptar: ReservationNoAdaptar)
    }

    var onReservationDetailsListener: OnReservationDetailsListener? = null

    inner class ReservationNoViewModel(view: View) : RecyclerView.ViewHolder(view) {
        val binding: UserViewBinding = UserViewBinding.bind(view)


        init {
            binding.root.setOnClickListener {
                onReservationDetailsListener?.onreservationdetailsCLick(
                    guests[adapterPosition],
                    adapterPosition,
                    this@ReservationNoAdaptar
                )
            }
        }
    }

    override fun getItemCount() = guests.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationNoViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_view, parent, false)
        return ReservationNoViewModel(view)
    }

    override fun onBindViewHolder(holder: ReservationNoViewModel, position: Int) {
        val user3 = guests[position]
        val fullName = "${user3.firstName} ${user3.lastName}"
        holder.binding.text1.text = fullName
        holder.binding.text2.text = user3.isCheckedIn
        holder.binding.text3.text = user3.isOnboard
        holder.binding.text4.text = user3.cabinNo.toString()
    }

    fun updateGuests(newGuests: List<User3>) {
        guests.clear()
        guests.addAll(newGuests)
        notifyDataSetChanged()
    }
}