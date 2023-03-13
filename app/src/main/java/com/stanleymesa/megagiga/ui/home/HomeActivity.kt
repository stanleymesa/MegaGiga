package com.stanleymesa.megagiga.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}