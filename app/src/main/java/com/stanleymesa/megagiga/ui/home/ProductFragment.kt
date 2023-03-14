package com.stanleymesa.megagiga.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.stanleymesa.core.ui.ProductAdapter
import com.stanleymesa.core.utlis.*
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.FragmentProductBinding
import com.stanleymesa.megagiga.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(), ProductAdapter.OnProductClickCallback,
    LoadingStateAdapter.OnPagingErrorCallback {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val productAdapter = ProductAdapter(this)
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
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
                viewModel.getProduct(token.tokenFormat())
            }
        }

        viewModel.getProductResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { data ->
                productAdapter.submitData(lifecycle, data)

            }
        }
    }

    private fun setAdapter() {
        binding.rvProduct.apply {
            val margin = 24
            addItemDecoration(MarginItemDecoration(margin.toPixel(requireActivity())))
            adapter = productAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter({ productAdapter.retry() }, this@ProductFragment)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onProductClicked(id: Int) {
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