package com.example.logbook.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logbook.databinding.FragmentDashboardBinding
import com.example.logbook.ui.helpers.FootConverterHelper
import com.example.logbook.ui.helpers.MeterConverterHelper
import com.example.logbook.ui.helpers.MileConverterHelper
import com.example.logbook.ui.helpers.MillimetreConverterHelper

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up unit selector with Spinner
        val unitsArray = arrayOf("Meter", "Millimeter", "Mile", "Foot")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, unitsArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.unitSelector.adapter = spinnerAdapter

        // Set up convert button listener
        binding.convertButton.setOnClickListener {
            performConversion()
        }

        return root
    }

    private fun performConversion() {
        val valueInput = binding.inputValue.text.toString()
        val selectedUnit = binding.unitSelector.selectedItem.toString()

        if (valueInput.isEmpty()) {
            clearTable()
            addRowToTable("Error", "Please enter a value")
            return
        }

        // Check for valid integer input
        val value = valueInput.toIntOrNull()
        if (value == null) {
            clearTable()
            addRowToTable("Error", "Invalid input. Please enter a valid number.")
            return
        }

        // Clear any previous results
        clearTable()

        // Conversion logic
        val results = when (selectedUnit) {
            "Meter" -> {
                val meterHelper = MeterConverterHelper(value)
                meterHelper.convertFromMeterToUnits().toList()
            }
            "Millimeter" -> {
                val mmHelper = MillimetreConverterHelper(value)
                mmHelper.convertFromMillimetreToUnits().toList()
            }
            "Mile" -> {
                val mileHelper = MileConverterHelper(value)
                mileHelper.convertFromMileToUnits().toList()
            }
            "Foot" -> {
                val footHelper = FootConverterHelper(value)
                footHelper.convertFromFootToUnits().toList()
            }
            else -> listOf()
        }

        // Display results in table
        results.forEach { result ->
            addRowToTable(result.unit.toString(), result.value.toString())
        }
    }

    private fun clearTable() {
        // Clear all rows except the header (if any)
        binding.resultTable.removeAllViews()
    }

    private fun addRowToTable(value: String, label: String) {
        val tableRow = TableRow(requireContext())

        // Create TextView for value (converted value)
        val valueTextView = TextView(requireContext())
        valueTextView.text = value
        valueTextView.setPadding(16, 16, 16, 16)
        valueTextView.gravity = android.view.Gravity.CENTER
        valueTextView.setTextColor(android.graphics.Color.BLACK)
        valueTextView.setBackgroundColor(android.graphics.Color.WHITE)
        valueTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

        // Create TextView for label (unit name)
        val labelTextView = TextView(requireContext())
        labelTextView.text = label
        labelTextView.setPadding(16, 16, 16, 16)
        labelTextView.gravity = android.view.Gravity.CENTER
        labelTextView.setTextColor(android.graphics.Color.BLACK)
        labelTextView.setBackgroundColor(android.graphics.Color.WHITE)
        labelTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

        // Add TextViews to TableRow in the correct order (value first, then label)
        tableRow.addView(labelTextView)
        tableRow.addView(valueTextView)

        // Add a divider line between rows
        val divider = View(requireContext())
        divider.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2)
        divider.setBackgroundColor(android.graphics.Color.BLACK)

        // Add the row to the table
        binding.resultTable.addView(tableRow)
        binding.resultTable.addView(divider)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
