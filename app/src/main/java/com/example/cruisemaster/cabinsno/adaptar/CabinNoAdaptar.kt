package com.example.cruisemaster.cabinsno.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.cruisemaster.R
import com.example.cruisemaster.cabinsno.model.User2

import com.example.cruisemaster.databinding.UserViewBinding
import com.example.cruisemaster.reservationno.model.User3


class CabinNoAdaptar(private var guests: ArrayList<User2>) :
    RecyclerView.Adapter<CabinNoAdaptar.ReservationNoViewModel>() {

    interface OnReservationDetailsListener {
        fun onreservationdetailsCLick(user2: User2, position: Int, cabinNoAdaptar: CabinNoAdaptar)
    }

    var onReservationDetailsListener: OnReservationDetailsListener? = null

    inner class ReservationNoViewModel(view: View) : RecyclerView.ViewHolder(view) {
        val binding: UserViewBinding = UserViewBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                onReservationDetailsListener?.onreservationdetailsCLick(
                    guests[adapterPosition],
                    adapterPosition,
                    this@CabinNoAdaptar
                )
            }
        }
    }

    override fun getItemCount() = guests.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationNoViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cabin_view, parent, false)
        return ReservationNoViewModel(view)
    }

    override fun onBindViewHolder(holder: ReservationNoViewModel, position: Int) {
        val user2 = guests[position]
        val fullName = "${user2.firstName} ${user2.lastName}"
        holder.binding.text1.text = fullName
        holder.binding.text2.text = user2.isCheckedIn
        holder.binding.text3.text = user2.isOnboard
        holder.binding.text4.text = user2.cabinNo.toString()
    }

    fun updateGuests(newGuests: List<User2>) {
        guests.clear()
        guests.addAll(newGuests)
        notifyDataSetChanged()
    }
}