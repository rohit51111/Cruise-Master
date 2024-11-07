package com.example.cruisemaster.searchnumber

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager


import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.cruisemaster.R
import com.example.cruisemaster.cabinsno.fragment.CabinNoFragment
import com.example.cruisemaster.cabinsno.model.CabinNoApiService
import com.example.cruisemaster.cabinsno.model.CabinNoRespositary

import com.example.cruisemaster.databinding.FragmentSearchNumberBinding
import com.example.cruisemaster.guestdetails.fragment.GuestNoFragment
import com.example.cruisemaster.reservationno.fragment.ReservationNoFragment
import com.example.cruisemaster.reservationno.model.ReservationNoApiService
import com.example.cruisemaster.reservationno.repositary.ReservationNoRespositary
import kotlinx.coroutines.launch


class SearchNumberFragment : Fragment() {
    private lateinit var binding: FragmentSearchNumberBinding
    private lateinit var reservationNoRespositary: ReservationNoRespositary
    private lateinit var cabinNoRespositary: CabinNoRespositary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchNumberBinding.inflate(layoutInflater)
        reservationNoRespositary = ReservationNoRespositary(ReservationNoApiService.getInstance())
        cabinNoRespositary = CabinNoRespositary(CabinNoApiService.getInstance())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val voyageNum = arguments?.getString("VoyageNumber")




        binding.reservationno.setOnClickListener {
            val reservationNumber = binding.editext1.text.toString()

            if (reservationNumber.isNotBlank()) {
                lifecycleScope.launch {
                    try {
                        val response = reservationNoRespositary.fetchGuestsByReservationNumber(reservationNumber)
                        if (response.guests.isNotEmpty()) {
                            val reservationNoFragment = ReservationNoFragment().apply {
                                arguments = Bundle().apply {
                                    putString("reservation_number", reservationNumber)
                                }
                            }
                            parentFragmentManager.beginTransaction()
                                .add(R.id.mainContainer, reservationNoFragment)
                                .addToBackStack(null)
                                .commit()
                        } else {
                            Toast.makeText(requireContext(), "Reservation number not found", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Reservation Number Not Found", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter reservation number", Toast.LENGTH_SHORT).show()
            }

            binding.editext1.text.clear()
            hideKeyboard(it)
        }

        binding.cabinno.setOnClickListener {
            val cabinNumber = binding.editext2.text.toString()

            if (cabinNumber.isNotBlank()) {
                lifecycleScope.launch {
                    try {
                        val response = cabinNoRespositary.fetchGuestsByCabinNumber(cabinNumber)
                        if (response.guests.isNotEmpty()) {
                            val cabinNoFragment = CabinNoFragment().apply {
                                arguments = Bundle().apply {
                                    putString("cabin_number", cabinNumber)
                                }
                            }
                            parentFragmentManager.beginTransaction()
                                .add(R.id.mainContainer, cabinNoFragment)
                                .addToBackStack(null)
                                .commit()
                        } else {
                            Toast.makeText(requireContext(), "Cabin number not found", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Cabin number not found", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter cabin number", Toast.LENGTH_SHORT).show()
            }

            binding.editext2.text.clear()
            hideKeyboard(it)
        }

        binding.allguestDetails.setOnClickListener {


            val guestNoFragment = GuestNoFragment()
            val args = Bundle().apply {
                putString("VoyageNumber", voyageNum)
            }
            guestNoFragment.arguments = args

            parentFragmentManager.beginTransaction()
                .add(R.id.mainContainer, guestNoFragment, null)
                .addToBackStack(null)
                .commit()
        }


    }
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

