package com.example.cruisemaster.againcrewdetails.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.R
import com.example.cruisemaster.againcrewdetails.factory.CrewDeatilViewModelFactory
import com.example.cruisemaster.againcrewdetails.model.CrewDetailsApiService
import com.example.cruisemaster.againcrewdetails.repositary.CrewDetailsRepositary
import com.example.cruisemaster.againcrewdetails.viewmodel.CrewDetailsViewModel
import com.example.cruisemaster.againguestdetails.factory.GuestDeatilViewModelFactory
import com.example.cruisemaster.againguestdetails.network.GuestDetailsApiService
import com.example.cruisemaster.againguestdetails.repositary.GuestDetailsRepositary
import com.example.cruisemaster.againguestdetails.viewmodel.GuestDetailsViewModel
import com.example.cruisemaster.chek_in_out.factory.ViewModelProviderFactory
import com.example.cruisemaster.chek_in_out.fragment.CheckInFragment
import com.example.cruisemaster.chek_in_out.fragment.CheckoutFragment
import com.example.cruisemaster.chek_in_out.network.CheckApiService
import com.example.cruisemaster.chek_in_out.repositary.CheckRepository
import com.example.cruisemaster.chek_in_out.viewmodel.CheckViewModel
import com.example.cruisemaster.databinding.FragmentAgainCrewDetailsBinding
import com.example.cruisemaster.databinding.FragmentGuestDetailsBinding
import com.example.cruisemaster.onboard_Status.factory.OnBoardViewModelProviderFactory
import com.example.cruisemaster.onboard_Status.fragment.OffBoardFragment
import com.example.cruisemaster.onboard_Status.fragment.OnBoardFragment
import com.example.cruisemaster.onboard_Status.network.OnBoardApiService
import com.example.cruisemaster.onboard_Status.repositary.OnBoardRepository
import com.example.cruisemaster.onboard_Status.viewmodel.OnBoardViewModel


class CrewDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAgainCrewDetailsBinding
    private lateinit var guestDetailsViewModel: CrewDetailsViewModel
    private lateinit var checkViewModel: CheckViewModel
    private lateinit var onBoardViewModel: OnBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAgainCrewDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        initViewModels() // Initialize the view models
        binding.viewmodel = guestDetailsViewModel // Set the view model in the binding

        initObserver()
        initListener()

        arguments?.getString("personId")?.let {
            guestDetailsViewModel.fetchGuestDetails(it)
        }

        return binding.root
    }

    private fun initListener() {
        binding.checkin.setOnClickListener {
            checkInOrOut()
        }

        binding.onboard.setOnClickListener {
            onboardOrOffboard()
        }
    }

    private fun checkInOrOut() {

        val personId = arguments?.getString("personId") ?: return
        val newStatus = !(guestDetailsViewModel.isCheckedIn.value ?: false)
        checkViewModel.check(personId, newStatus.toString())


    }


    private fun onboardOrOffboard() {
        val personId = arguments?.getString("personId") ?: return
        if (guestDetailsViewModel.isCheckedIn.value == true) {
            val newStatus = !(guestDetailsViewModel.isOnBoard.value ?: false)
            onBoardViewModel.onBoard(personId, newStatus.toString())
        }
        else{
            Toast.makeText(context, "Please Check In First Without That You Can Not On Board", Toast.LENGTH_SHORT).show()
        }
    }



    private fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.mainContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



    private fun initObserver() {
        guestDetailsViewModel.guestDetailsMutableLiveData.observe(viewLifecycleOwner) { guestDetails ->
            guestDetails?.let {
                binding.guestdetail = it
                guestDetailsViewModel.updateCheckInStatus(it.isCheckedIn.equals("yes", ignoreCase = true))
                guestDetailsViewModel.updateOnBoardStatus(it.isOnboard.equals("yes", ignoreCase = true))
            }
        }

        checkViewModel.checkStatus.observe(viewLifecycleOwner) { success ->
            if (success != null) {
                val currentStatus = guestDetailsViewModel.isCheckedIn.value!!
                guestDetailsViewModel.updateCheckInStatus(!currentStatus)
                if (currentStatus) {
                    openFragment(CheckoutFragment())
                } else {
                    openFragment(CheckInFragment())
                }
            }
        }

        guestDetailsViewModel.isCheckedIn.observe(viewLifecycleOwner) { isCheckedIn ->
            binding.checkin.text = if (isCheckedIn) getString(R.string.check_out) else getString(R.string.check_in)
        }

        onBoardViewModel.onBoardStatus.observe(viewLifecycleOwner) { success ->
            if (success != null) {
                val currentStatus = guestDetailsViewModel.isOnBoard.value!!
                guestDetailsViewModel.updateOnBoardStatus(!currentStatus)
                if (currentStatus) {
                    openFragment(OffBoardFragment())
                } else {
                    openFragment(OnBoardFragment())
                }
            }
        }

        guestDetailsViewModel.isOnBoard.observe(viewLifecycleOwner) { isOnBoard ->
            binding.onboard.text = if (isOnBoard) getString(R.string.off_board) else getString(R.string.on_board)
        }
    }

    private fun updateCheckInButtonText() {
        val isCheckedIn = guestDetailsViewModel.isCheckedIn.value ?: false
        binding.checkin.text = if (isCheckedIn) "Check Out" else "Check In"
    }

    private fun initViewModels() {
        guestDetailsViewModel = ViewModelProvider(
            this,
            CrewDeatilViewModelFactory(
                CrewDetailsRepositary(
                    CrewDetailsApiService.getInstance()
                )
            )
        )[CrewDetailsViewModel::class.java]

        checkViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(
                CheckRepository(
                    CheckApiService.getInstance()
                )
            )
        )[CheckViewModel::class.java]


        onBoardViewModel = ViewModelProvider(
            this,
            OnBoardViewModelProviderFactory(
                OnBoardRepository(
                    OnBoardApiService.getInstance()
                )
            )
        )[OnBoardViewModel::class.java]

    }
}