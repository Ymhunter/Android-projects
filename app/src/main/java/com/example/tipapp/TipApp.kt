package com.example.tipapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.tipapp.databinding.ActivityTipappBinding

class TipApp : AppCompatActivity() {
    private lateinit var binding: ActivityTipappBinding
    private var percentageCounter = 0.00
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTipappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar()
        binding.btnPlus.setOnClickListener {
            percentageCounter += 1.0
            val (tipAmount, totalAmount, bilAmount) = calculetingTips()
            binding.percentage.text = "$percentageCounter%"
            displayingResults(totalAmount, tipAmount, bilAmount)
        }
        binding.btnMinus.setOnClickListener {
            if (percentageCounter < 1.0) percentageCounter = 0.0 else percentageCounter -= 1.0
            val (tipAmount, totalAmount, bilAmount) = calculetingTips()
            binding.percentage.text = "$percentageCounter%"
            displayingResults(totalAmount, tipAmount, bilAmount)
        }
        binding.editTextBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val (tipAmount, totalAmount, bilAmount) = calculetingTips()
                displayingResults(totalAmount, tipAmount, bilAmount)
            }
        })
    }

    private fun displayingResults(
        totalAmount: Double,
        tipAmount: Double,
        bilAmount: Double
    ) {
        binding.totalAmount.text = "%.2f".format(totalAmount)
        binding.TextViewTip.text = "%.2f".format(tipAmount)
        binding.textViewBill.text = "%.2f".format(bilAmount)
    }

    private fun calculetingTips(): Triple<Double, Double, Double> {
        val baseAmount = binding.editTextBillAmount.text.toString().toDoubleOrNull()
        val tipPercent = percentageCounter / basisPoint
        val tipAmount = (baseAmount?.times(tipPercent) ?: 0.00)
        val totalAmount = baseAmount?.plus(tipAmount) ?: 0.00
        val bilAmount = baseAmount ?: 0.00
        return Triple(tipAmount, totalAmount, bilAmount)
    }

    private fun configureToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    companion object {
        private const val basisPoint = 100
    }
}