package com.example.cruisemaster.viewdetails.fragment
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cruisemaster.R
import com.example.cruisemaster.constant.DEPARTMENT_NAME
import com.example.cruisemaster.constant.SHIP_ID
import com.example.cruisemaster.constant.VYOG_NUM
import com.example.cruisemaster.crewdetails.fragment.AllCrewFragment
import com.example.cruisemaster.databinding.FragmentViewdetailsBinding
import com.example.cruisemaster.localdb.repository.CruiseRepository
import com.example.cruisemaster.login.activity.LoginActivity
import com.example.cruisemaster.searchnumber.SearchNumberFragment
import com.example.cruisemaster.viewdetails.adapter.DepartmentSpinnerAdapter
import com.example.cruisemaster.viewdetails.adapter.VoyageNumberAdapter
import com.example.cruisemaster.viewdetails.viewmodel.VoyageNumberViewModel

class ViewDetailsFragment : Fragment() {

    private val viewModel : VoyageNumberViewModel by activityViewModels()
    private var selectedVoyageNum: String? = null
    private var selectedDepartmentName: String? = null
    private lateinit var voyageAdapter: VoyageNumberAdapter
    private lateinit var departmentSpinnerAdapter: DepartmentSpinnerAdapter
    private lateinit var binding: FragmentViewdetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewdetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = CruiseRepository(requireContext())

        // For voyage number
        // Observe voyages LiveData in ViewModel
        viewModel.voyages.observe(viewLifecycleOwner, Observer { voyages ->
            if (voyages != null && voyages.isNotEmpty()){
                voyageAdapter = VoyageNumberAdapter(requireContext(), voyages, R.layout.voyagespinner_item)
                binding.guestSpinner.adapter = voyageAdapter
            }

        })


        // Ship Id for Voyage number
        arguments?.getString(SHIP_ID)?.let { shipId ->
            viewModel.fetchVoyages(shipId)
        }

        // Voyage number Item Selected
        binding.guestSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val voyage = viewModel.voyages.value?.get(position)
                viewModel.selectedVoyageNumber.value = voyage?.voyageNumber
                selectedVoyageNum = voyage?.voyageNumber
                voyageAdapter.setSelectedPosition(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected

            }
        }



        binding.submit1.setOnClickListener {
            selectedVoyageNum?.let { voyageNum ->

                val fragment = SearchNumberFragment().apply {
                    arguments = Bundle().apply {
                        putString("VoyageNumber", voyageNum)
                        Log.d("ji", "Response: $voyageNum")
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment, null)
                    .addToBackStack(null)
                    .commit()
            }
        }

        viewModel.departments.observe(viewLifecycleOwner, Observer { departments ->
            if (departments != null && departments.isNotEmpty()){
                departmentSpinnerAdapter = DepartmentSpinnerAdapter(requireContext(), R.layout.crewdepartmentspinner_item, departments)
                binding.crewSpinner.adapter = departmentSpinnerAdapter
            }
        })


        arguments?.getString(SHIP_ID)?.let { shipId ->
            viewModel.fetchDepartments(shipId)
        }


        // Department Item Selected
        binding.crewSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedDepartmentName = viewModel.departments.value?.get(position)
                departmentSpinnerAdapter.setSelectedPosition(position)
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }


        binding.departmentSubmit.setOnClickListener {
            selectedDepartmentName?.let { departmename ->
                val fragment = AllCrewFragment().apply {
                    arguments = Bundle().apply {
                        putString("DepartmentName", departmename)
                        Log.d("ji", "Response: $departmename")
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment, null)
                    .addToBackStack(null)
                    .commit()
            }
        }

        // Download Manifest
        binding.downloadmanifest.setOnClickListener {
            selectedVoyageNum?.let { voyageNum ->
                Log.d("VoyageNumber",voyageNum)
                repository.downloadManifest(voyageNum) { success, error ->
                    if (success) {
                        Toast.makeText(context, "Manifest downloaded successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = if (error?.contains("No active voyage found") == true) {
                            "No active voyage found for the given voyage number. Please check the voyage number and try again."
                        } else {
                            "Failed to download manifest: $error"
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            } ?: Toast.makeText(context, "Please select a voyage number", Toast.LENGTH_SHORT).show()
        }

    }

}








