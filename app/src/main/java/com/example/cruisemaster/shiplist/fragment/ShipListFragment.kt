package com.example.cruisemaster.shiplist.fragment

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.example.cruisemaster.R
import com.example.cruisemaster.constant.SHIP_ID
import com.example.cruisemaster.crewdetails.fragment.AllCrewFragment
import com.example.cruisemaster.databinding.FragmentShipListBinding
import com.example.cruisemaster.login.activity.LoginActivity
import com.example.cruisemaster.searchnumber.SearchNumberFragment
import com.example.cruisemaster.shiplist.adapter.ShipListAdapter
import com.example.cruisemaster.shiplist.viewmodel.ShipListViewModel
import com.example.cruisemaster.splashscreen.SplashActivity
import com.example.cruisemaster.viewdetails.fragment.ViewDetailsFragment


class ShipListFragment : Fragment() {

    private val viewModel: ShipListViewModel by activityViewModels()
    private var selectedShipId: String? = null
    private lateinit var binding: FragmentShipListBinding
    private lateinit var adapter: ShipListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShipListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener {
            logout()
        }

        viewModel.ships.observe(viewLifecycleOwner, Observer { ships ->
            if (ships != null && ships.isNotEmpty()) {
                adapter = ShipListAdapter(requireContext(), ships, R.layout.shipspinner_item)
                binding.spinner.adapter = adapter
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        })

        viewModel.fetchShips()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val ship = viewModel.ships.value?.get(position)
                viewModel.selectedShipName.value = ship?.shipName
                selectedShipId = ship?.shipId

                adapter.setSelectedPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.proceedButton.setOnClickListener {
            selectedShipId?.let { shipId ->
                val fragment = ViewDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(SHIP_ID, shipId)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment, null)
                    .addToBackStack("ViewDetailsFragment")
                    .commit()
            } ?: Toast.makeText(requireContext(), "Please select a ship", Toast.LENGTH_SHORT).show()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitConfirmationDialog()
                }
            }
        )
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                // Close the app
                val activityManager = requireContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                activityManager.killBackgroundProcesses(requireContext().packageName)
                requireActivity().finishAffinity() // Close all activities in this task
                System.exit(0) // Exit the process
            }
            .setNegativeButton("No", null)  // Dismiss the dialog
            .show()
    }

    private fun logout() {
        // Clear user data if necessary
        val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Clear activity stack and navigate to LoginActivity
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()  // Ensure current activity is finished
    }

}
