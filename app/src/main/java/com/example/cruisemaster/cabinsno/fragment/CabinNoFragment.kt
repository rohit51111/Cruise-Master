package com.example.cruisemaster.cabinsno.fragment

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
import com.example.cruisemaster.cabinsno.adaptar.CabinNoAdaptar
import com.example.cruisemaster.cabinsno.factory.CabinNoViewModelfactory
import com.example.cruisemaster.cabinsno.model.CabinNoApiService
import com.example.cruisemaster.cabinsno.model.CabinNoRespositary
import com.example.cruisemaster.cabinsno.model.User2
import com.example.cruisemaster.cabinsno.viewmodel.CabinNoViewModel
import com.example.cruisemaster.databinding.FragmentCabinNoBinding

import com.example.cruisemaster.databinding.FragmentReservationNoBinding
import com.example.cruisemaster.reservationno.adaptar.ReservationNoAdaptar
import com.example.cruisemaster.reservationno.factory.ReservationNoViewModelfactory
import com.example.cruisemaster.reservationno.model.ReservationNoApiService
import com.example.cruisemaster.reservationno.model.User3
import com.example.cruisemaster.reservationno.repositary.ReservationNoRespositary
import com.example.cruisemaster.reservationno.viewmodel.ReservationNoViewModel


class CabinNoFragment : Fragment() {

    private lateinit var binding: FragmentCabinNoBinding
    private lateinit var cabinNoViewModel: CabinNoViewModel
    private lateinit var cabinNoAdaptar: CabinNoAdaptar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentCabinNoBinding.inflate(layoutInflater)
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
            val cabinNumber = args.getString("cabin_number")
            cabinNumber?.let { cabinNoViewModel.fetchGuestsByCabinNumber(it) }
        }

        cabinNoAdaptar.onReservationDetailsListener = object : CabinNoAdaptar.OnReservationDetailsListener {
            override fun onreservationdetailsCLick(user2: User2, position: Int, cabinNoAdaptar: CabinNoAdaptar) {
                showDetailsFragment(user2.personId)
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
        cabinNoAdaptar = CabinNoAdaptar(ArrayList())
        binding.recyclerUsers.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cabinNoAdaptar
        }
    }

    private fun initObserver() {
        cabinNoViewModel.guestsLiveData.observe(viewLifecycleOwner, Observer { guests ->
            guests?.let { cabinNoAdaptar.updateGuests(it) }
        })

        cabinNoViewModel.countLiveData.observe(viewLifecycleOwner, Observer { count ->
            count?.let { binding.textViewCount.text = "$it" }
        })

        cabinNoViewModel.cabinNumberLiveData.observe(viewLifecycleOwner, Observer { cabinNo ->
            cabinNo?.let { binding.cabinno.text = "$it" }
        })
    }

    private fun initViewModels() {
        cabinNoViewModel = ViewModelProvider(
            this,
            CabinNoViewModelfactory(
                CabinNoRespositary(
                    CabinNoApiService.getInstance()
                )
            )
        )[CabinNoViewModel::class.java]
    }


   /* private fun initListeners() {
        val args = arguments
        if (args != null) {
            val cabinNumber = args.getString("cabin_number")
            cabinNumber?.let { cabinNoViewModel.fetchGuestsByCabinNumber(it) }
        }

        cabinNoAdaptar.onReservationDetailsListener = object : CabinNoAdaptar.OnReservationDetailsListener {
            override fun onreservationdetailsCLick(user2: User2, position: Int, cabinNoAdaptar: CabinNoAdaptar) {
                showDetailsFragment(user2.personId)
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
        cabinNoAdaptar = CabinNoAdaptar(ArrayList())
        binding.recyclerUsers.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cabinNoAdaptar
        }
    }

    private fun initObserver() {
        cabinNoViewModel.guestsLiveData.observe(viewLifecycleOwner, Observer { guests ->
            guests?.let { cabinNoAdaptar.updateGuests(it) }
        })

        cabinNoViewModel.countLiveData.observe(viewLifecycleOwner, Observer { count ->
            count?.let { binding.textViewCount.text = "$it" }
        })

        cabinNoViewModel.cabinNumberLiveData.observe(viewLifecycleOwner, Observer { cabinNo ->
            cabinNo?.let { binding.cabinno.text = "$it" }
        })

    }

    private fun initViewModels() {
        cabinNoViewModel = ViewModelProvider(
            this,
            CabinNoViewModelfactory(
                CabinNoRespositary(
                    CabinNoApiService.getInstance()
                )
            )
        )[CabinNoViewModel::class.java]
    }*/

}