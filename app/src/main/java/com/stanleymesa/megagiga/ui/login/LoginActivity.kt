package com.stanleymesa.megagiga.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setTabWithViewPager()
    }

    private fun setTabWithViewPager() {
        val loginPagerAdapter = LoginPagerAdapter(this)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = loginPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.login)
                1 -> tab.text = getString(R.string.register)
            }
        }.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}