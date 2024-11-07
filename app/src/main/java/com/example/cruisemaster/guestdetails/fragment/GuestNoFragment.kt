package com.example.cruisemaster.guestdetails.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cruisemaster.R
import com.example.cruisemaster.againguestdetails.fragment.GuestDetailsFragment


import com.example.cruisemaster.databinding.FragmentGuestNoBinding
import com.example.cruisemaster.guestdetails.adaptar.GuestNoAdaptar
import com.example.cruisemaster.guestdetails.factory.GuestNoViewModelfactory
import com.example.cruisemaster.guestdetails.model.User4
import com.example.cruisemaster.guestdetails.network.GuestNoApiService
import com.example.cruisemaster.guestdetails.repositary.GuestNoRespositary
import com.example.cruisemaster.guestdetails.viewmodel.GuestNoViewModel

class GuestNoFragment : Fragment() {

    private lateinit var binding: FragmentGuestNoBinding
    private lateinit var guestNoViewModel: GuestNoViewModel
    private lateinit var guestNoAdapter: GuestNoAdaptar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGuestNoBinding.inflate(layoutInflater)
        val voyageNumber = arguments?.getString("VoyageNumber")

        initViewModels()
        initViews()
        initObserver()
        initAdapter()
        initListeners()

        voyageNumber?.let {
            guestNoViewModel.setVoyageNumber(it)
            guestNoViewModel.fetchShipName()
        }

        return binding.root
    }

    private fun initListeners() {
        binding.recyclerUsers.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        guestNoViewModel.fetchShipName()
                    }
                }
            }
        )

        guestNoAdapter.onCreawDetailsListener = object : GuestNoAdaptar.OnCreawDetailsListener {
            override fun oncreawdetailsCLick(user4: User4, position: Int, guestNoAdapter: GuestNoAdaptar) {
                showDetailsFragment(user4.personId)
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
        guestNoAdapter = GuestNoAdaptar(guestNoViewModel.shipnameMutableLiveData.value ?: arrayListOf<User4>())
        binding.recyclerUsers.adapter = guestNoAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserver() {
        guestNoViewModel.shipnameMutableLiveData.observe(
            viewLifecycleOwner
        ) { response ->

            response?.let {
                guestNoAdapter.guests.clear()
                guestNoAdapter.guests.addAll(it)
                guestNoAdapter.notifyDataSetChanged()
                binding.textCount.text = "${it.size}"
            }
        }

        guestNoViewModel.checkedInCount.observe(viewLifecycleOwner) { count ->
            binding.textCount2.text = "$count"
        }

        guestNoViewModel.onboardedCount.observe(viewLifecycleOwner) { count ->
            binding.textCount3.text = "$count"
        }

        guestNoViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModels() {
        guestNoViewModel = ViewModelProvider(
            this,
            GuestNoViewModelfactory(GuestNoRespositary(GuestNoApiService.getInstance()))
        )[GuestNoViewModel::class.java]
    }

    private fun initViews() {
        // Any additional UI initialization can go here
    }
}