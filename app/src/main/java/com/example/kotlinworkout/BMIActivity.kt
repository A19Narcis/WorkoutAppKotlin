package com.example.kotlinworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import com.example.kotlinworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding : ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

        //We see the Metric system by default
        makeVisibleMetricsUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricsUnitsView()
            } else if (checkedId == R.id.rbUsUnits){
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleMetricsUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.tilUsUnitWeight?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE

        binding?.tilUsUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE
        binding?.etUsUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty()-> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNITS_VIEW){
            if (validateMetricUnits()){
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResults(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        } else if (currentVisibleView == US_UNITS_VIEW){
            if (validateUsUnits()){
                val heightInFeetValue : String = binding?.etUsMetricUnitHeightFeet?.text.toString()
                val heightInInchValue : String = binding?.etUsMetricUnitHeightInch?.text.toString()
                val weightValue : Float = binding?.etUsUnitWeight?.text.toString().toFloat()

                val heightValue = heightInInchValue.toFloat() + heightInFeetValue.toFloat() * 12

                val bmi = 703 * (weightValue / (heightValue * heightValue))

                displayBMIResults(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResults(bmi : Float){
        val bmiLabel : String
        val bmiDescription : String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

}