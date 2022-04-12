package com.example.okanekonversi

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.okanekonversi.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // Binding object objek dengan akses ke tampilan di layout activity_main.xml
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate file Layout XML dan mengembalikan instance objek yang di binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Atur content view ke root dari layout
        setContentView(binding.root)

        // Mengatur click listener untuk kalkulasi
        binding.hasil.isVisible = false
        binding.button.setOnClickListener { Konversian() }

        // Mengtur key listener editText yang berupa "Nilai mata uang" agar bisa memakai enter
        binding.inputan.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }


    // Method untuk melakukan konversikonversi
    @SuppressLint("SetTextI18n")
    private fun Konversian() {
        // Mengambil data decimal dari inputan "Nilai mata uang"
        val stringInTextField = binding.inputan.text.toString()
        val nilai = stringInTextField.toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (nilai == null || nilai == 0.0) {
            binding.hasil.text = ""
            return
        }

        // Nilai dari konversinya
        val konversi = when (binding.radioGroup1.checkedRadioButtonId) {
            R.id.euroButton -> 15552.89
            R.id.usdButton -> 14364.25
            R.id.jpyButton -> 114.50
            R.id.sarButton -> 3830.52
            else -> 0.0
        }

        // Hitungan kalkulasinya (nilai * konversi)
        val hitung = nilai * konversi
        // Print hasil dari kalkulasi
        val indonesianLocale = Locale("id", "ID")
        val formatHasil = NumberFormat.getCurrencyInstance(indonesianLocale).format(hitung)
        binding.hasil.text = "Nilai Rupiah: $formatHasil"
        binding.hasil.isVisible = true
    }

    /**
     * Key listener for hiding the keyboard when the "Enter" button is tapped.
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}