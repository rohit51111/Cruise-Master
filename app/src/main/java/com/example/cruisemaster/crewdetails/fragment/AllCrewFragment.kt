package com.example.cruisemaster.crewdetails.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cruisemaster.R
import com.example.cruisemaster.againcrewdetails.fragment.CrewDetailsFragment


import com.example.cruisemaster.crewdetails.adaptar.AllCrewAdaptar

import com.example.cruisemaster.crewdetails.factory.MyViewModelfactory
import com.example.cruisemaster.crewdetails.model.User
import com.example.cruisemaster.crewdetails.network.AllCrewApiService
import com.example.cruisemaster.crewdetails.repositary.AllCrewRespositary

import com.example.cruisemaster.crewdetails.viewmodel.AllCrewViewModel
import com.example.cruisemaster.databinding.FragmentAllCrewBinding


class AllCrewFragment : Fragment() {

    private lateinit var binding: FragmentAllCrewBinding
    private lateinit var allCrewViewModel: AllCrewViewModel
    private lateinit var allCrewAdaptar: AllCrewAdaptar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAllCrewBinding.inflate(layoutInflater)

        val voyageNumber = arguments?.getString("DepartmentName")

        initViewModels()
        initViews()
        initObserver()
        initAdapter()
        initListeners()

        voyageNumber?.let {
            allCrewViewModel.setVoyageNumber(it)
            allCrewViewModel.fetchShipName()
        }

        return binding.root
    }

    private fun initListeners() {
        binding.recyclerUsers.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        allCrewViewModel.fetchShipName()
                    }
                }
            }
        )

        allCrewAdaptar.onCreawDetailsListener = object :AllCrewAdaptar.OnCreawDetailsListener{
            override fun oncreawdetailsCLick(
                user: User,
                position: Int,
                allCrewAdaptar: AllCrewAdaptar
            ) {
                showDetailsFragment(user.personId)
            }
        }
    }

    private fun showDetailsFragment(personId: String) {
        val crewDetailsFragment = CrewDetailsFragment()
        val bundle = Bundle()
        bundle.putString("personId", personId)
        crewDetailsFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer, crewDetailsFragment, null)
            .addToBackStack(null)
            .commit()
    }

    private fun initAdapter(){
        allCrewAdaptar = AllCrewAdaptar(allCrewViewModel.shipnameMutableLiveData.value ?: arrayListOf<User>())
        binding.recyclerUsers.adapter = allCrewAdaptar
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserver() {
        allCrewViewModel.shipnameMutableLiveData.observe(
            viewLifecycleOwner
        ) { response ->
            response?.let {
                allCrewAdaptar.guests.clear()
                allCrewAdaptar.guests.addAll(it)
                allCrewAdaptar.notifyDataSetChanged()
                binding.textCount.text = "${it.size}"
            }
        }

        allCrewViewModel.checkedInCountLiveData.observe(
            viewLifecycleOwner
        ) { count ->
            binding.textCount2.text = "$count"
        }

        allCrewViewModel.onboardedCountLiveData.observe(
            viewLifecycleOwner
        ) { count ->
            binding.textCount3.text = "$count"
        }

        allCrewViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModels() {
        allCrewViewModel = ViewModelProvider(
            this,
            MyViewModelfactory(AllCrewRespositary(AllCrewApiService.getInstance()))
        )[AllCrewViewModel::class.java]
    }

    private fun initViews() {
        // Initialize any other views if necessary
    }
}