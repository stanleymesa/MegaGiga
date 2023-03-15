package com.stanleymesa.megagiga.ui.supplier

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.ui.ChooseSupplierAdapter
import com.stanleymesa.core.utlis.*
import com.stanleymesa.megagiga.databinding.ActivitySupplierBinding
import com.stanleymesa.megagiga.ui.home.SupplierViewModel
import com.stanleymesa.megagiga.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupplierActivity : AppCompatActivity(),
    LoadingStateAdapter.OnPagingErrorCallback, ChooseSupplierAdapter.OnSupplierClickCallback {

    private var _binding: ActivitySupplierBinding? = null
    private val binding get() = _binding!!
    private val supplierAdapter = ChooseSupplierAdapter(this)
    private val viewModel: SupplierViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.toolbar.ivBack.isVisible = true
        binding.toolbar.tvTitle.text = "Choose Supplier"
        setAdapter()
        observeData()
    }

    private fun observeData() {
        viewModel.getToken().observe(this) { event ->
            event.getContentIfNotHandled()?.let { token ->
                viewModel.getSupplier(token.tokenFormat())
            }
        }

        viewModel.getProductResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { data ->
                supplierAdapter.submitData(lifecycle, data)
            }
        }
    }

    private fun setAdapter() {
        binding.rvSupplier.apply {
            val margin = 24
            addItemDecoration(MarginItemDecoration(margin.toPixel(this@SupplierActivity)))
            adapter = supplierAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter({ supplierAdapter.retry() }, this@SupplierActivity)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTokenExpired() {
        Toast.makeText(this, "Session expired, need to login again!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onSupplierClicked(supplier: Supplier) {
        val intent = Intent()
        intent.putExtra(INTENT_SUPPLIER, supplier)
        setResult(GET_SUPPLIER, intent)
        finish()
    }

}