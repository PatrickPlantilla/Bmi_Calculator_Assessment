package com.example.bmicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.bmicalculator.databinding.ActivityMainBinding
import com.example.bmicalculator.databinding.PopupBmiResultsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weightpicker.minValue = 30
        binding.weightpicker.maxValue = 150

        binding.heightpicker.minValue = 100
        binding.heightpicker.maxValue = 250

        binding.button.setOnClickListener {
            showBMIPopup()
        }
    }

    private fun calculateBMI(): Double {
        val height = binding.heightpicker.value
        val doubleHeight = height.toDouble() / 100

        val weight = binding.weightpicker.value

        return weight.toDouble() / (doubleHeight * doubleHeight)
    }

    private fun healthyMessage(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Healthy"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }

    private fun showBMIPopup() {
        val bmi = calculateBMI()
        val bmiMessage = String.format("Your BMI is: %.2f", bmi)
        val healthyMessage = String.format("Considered: %s", healthyMessage(bmi))

        val popupBinding = PopupBmiResultsBinding.inflate(LayoutInflater.from(this))

        popupBinding.resultsText.text = bmiMessage
        popupBinding.healthyText.text = healthyMessage

        val imageRes = when {
            bmi < 18.5 -> R.drawable.underweight
            bmi < 25.0 -> R.drawable.healthy
            bmi < 30.0 -> R.drawable.overweight
            else -> R.drawable.obese
        }
        popupBinding.bmiImage.setImageResource(imageRes)

        val dialog = AlertDialog.Builder(this)
            .setView(popupBinding.root)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()
    }
}
