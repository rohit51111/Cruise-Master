package com.example.cruisemaster.reservationno.fragment

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cruisemaster.R
import com.example.cruisemaster.againguestdetails.fragment.GuestDetailsFragment

import com.example.cruisemaster.databinding.FragmentReservationNoBinding
import com.example.cruisemaster.reservationno.adaptar.ReservationNoAdaptar
import com.example.cruisemaster.reservationno.factory.ReservationNoViewModelfactory
import com.example.cruisemaster.reservationno.model.ReservationNoApiService
import com.example.cruisemaster.reservationno.model.User3
import com.example.cruisemaster.reservationno.repositary.ReservationNoRespositary
import com.example.cruisemaster.reservationno.viewmodel.ReservationNoViewModel



class ReservationNoFragment : Fragment() {

    private lateinit var binding: FragmentReservationNoBinding
    private lateinit var reservationNoViewModel: ReservationNoViewModel
    private lateinit var reservationNoAdaptar: ReservationNoAdaptar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentReservationNoBinding.inflate(layoutInflater)
        binding.root.setOnClickListener { }

        initViewModels()
        initAdapter()
        initObserver()
        initListeners()

        return binding.root

    }






    private fun initListeners() {
        val args = arguments
        if (args != null) {
            val reservationNumber = args.getString("reservation_number")
            reservationNumber?.let { reservationNoViewModel.fetchGuestsByReservationNumber(it) }
        }

        reservationNoAdaptar.onReservationDetailsListener = object : ReservationNoAdaptar.OnReservationDetailsListener {
            override fun onreservationdetailsCLick(user3: User3, position: Int, reservationNoAdaptar: ReservationNoAdaptar) {
                showDetailsFragment(user3.personId)
            }
        }
    }

    private fun showDetailsFragment(personId: String) {
        val guestDetailsFragment = GuestDetailsFragment()
        val bundle = Bundle()
        bundle.putString("personId", personId)
        guestDetailsFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer, guestDetailsFragment, null)
            .addToBackStack(null)
            .commit()
    }

    private fun initAdapter() {
        reservationNoAdaptar = ReservationNoAdaptar(ArrayList())
        binding.recyclerUsers.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reservationNoAdaptar
        }
    }

    private fun initObserver() {
        reservationNoViewModel.guestsLiveData.observe(viewLifecycleOwner, Observer { guests ->
            guests?.let { reservationNoAdaptar.updateGuests(it) }
        })

        reservationNoViewModel.countLiveData.observe(viewLifecycleOwner, Observer { count ->
            count?.let { binding.textViewCount.text = " $it" }
        })

        reservationNoViewModel.reservationNumberLiveData.observe(viewLifecycleOwner, Observer { reservationNumber ->
            reservationNumber?.let { binding.reservatino.text = "$it" }
        })
    }

    private fun initViewModels() {
        reservationNoViewModel = ViewModelProvider(
            this,
            ReservationNoViewModelfactory(
                ReservationNoRespositary(
                    ReservationNoApiService.getInstance()
                )
            )
        )[ReservationNoViewModel::class.java]
    }
}