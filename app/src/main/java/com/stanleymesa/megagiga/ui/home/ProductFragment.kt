package com.stanleymesa.megagiga.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.stanleymesa.core.ui.ProductAdapter
import com.stanleymesa.core.utlis.*
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.FragmentProductBinding
import com.stanleymesa.megagiga.ui.create.CreateProductActivity
import com.stanleymesa.megagiga.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(), ProductAdapter.OnProductClickCallback,
    LoadingStateAdapter.OnPagingErrorCallback, View.OnClickListener {

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
        binding.fabProduct.setOnClickListener(this)
    }

    private fun observeData() {
        viewModel.getTokenResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { token ->
                Log.e("TOKEN", token)
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

    private fun scrollToTop() {
        productAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {}

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {}

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {}

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.rvProduct.scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {}

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {}
        })
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == REFRESH_PRODUCT) {
                productAdapter.refresh()
                scrollToTop()
                val snackbarValue = result.data?.getStringExtra(SNACKBAR_VALUE)
                snackbarValue?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).setAction("OK") {}.show()
                }
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
        Toast.makeText(requireContext(),
            "Session expired, need to login again!",
            Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_product -> {
                resultLauncher.launch(Intent(requireActivity(), CreateProductActivity::class.java))
            }
        }
    }

}