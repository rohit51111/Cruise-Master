package com.example.cruisemaster.chek_in_out.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cruisemaster.R
import com.example.cruisemaster.againguestdetails.fragment.GuestDetailsFragment
import com.example.cruisemaster.databinding.FragmentCheckInBinding
import com.example.cruisemaster.guestdetails.fragment.GuestNoFragment
import com.example.cruisemaster.shiplist.fragment.ShipListFragment


class CheckInFragment : Fragment() {

    private lateinit var binding: FragmentCheckInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckInBinding.inflate(inflater, container, false)
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.backji.setOnClickListener {
            val guestNoFragment = ShipListFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, guestNoFragment, null)
                .addToBackStack(null)
                .commit()
        }
    }
}