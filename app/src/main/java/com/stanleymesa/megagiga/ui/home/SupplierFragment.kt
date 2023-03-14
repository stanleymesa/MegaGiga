package com.stanleymesa.megagiga.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.stanleymesa.core.ui.SupplierAdapter
import com.stanleymesa.core.utlis.LoadingStateAdapter
import com.stanleymesa.core.utlis.MarginItemDecoration
import com.stanleymesa.core.utlis.toPixel
import com.stanleymesa.core.utlis.tokenFormat
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.FragmentSupplierBinding
import com.stanleymesa.megagiga.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupplierFragment : Fragment(), SupplierAdapter.OnSupplierClickCallback,
    LoadingStateAdapter.OnPagingErrorCallback {

    private var _binding: FragmentSupplierBinding? = null
    private val binding get() = _binding!!
    private val supplierAdapter = SupplierAdapter(this)
    private val viewModel: SupplierViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSupplierBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setAdapter()
        observeData()
    }

    private fun observeData() {
        viewModel.getToken().observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { token ->
                viewModel.getSupplier(token.tokenFormat())
            }
        }

        viewModel.getProductResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { data ->
                supplierAdapter.submitData(lifecycle, data)
            }
        }
    }

    private fun setAdapter() {
        binding.rvSupplier.apply {
            val margin = 24
            addItemDecoration(MarginItemDecoration(margin.toPixel(requireActivity())))
            adapter = supplierAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter({ supplierAdapter.retry() }, this@SupplierFragment)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupplierClicked(id: Int) {
    }

    override fun onEditClicked(id: Int) {
    }

    override fun onRemoveClicked(id: Int) {
    }

    override fun onTokenExpired() {
        Toast.makeText(requireContext(), "Session expired, need to login again!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }
}