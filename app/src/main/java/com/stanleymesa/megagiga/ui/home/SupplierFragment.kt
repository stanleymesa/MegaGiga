package com.stanleymesa.megagiga.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.ui.SupplierAdapter
import com.stanleymesa.core.utlis.*
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.FragmentSupplierBinding
import com.stanleymesa.megagiga.ui.create.CreateSupplierActivity
import com.stanleymesa.megagiga.ui.login.LoginActivity
import com.stanleymesa.megagiga.ui.update.UpdateSupplierActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupplierFragment : Fragment(), SupplierAdapter.OnSupplierClickCallback,
    LoadingStateAdapter.OnPagingErrorCallback, View.OnClickListener {

    private var _binding: FragmentSupplierBinding? = null
    private val binding get() = _binding!!
    private val supplierAdapter = SupplierAdapter(this)
    private var token = ""
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
        binding.fabSupplier.setOnClickListener(this)
    }

    private fun observeData() {
        viewModel.getTokenResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { token ->
                this.token = token
                viewModel.getSupplier(token.tokenFormat())
            }
        }

        viewModel.getProductResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { data ->
                supplierAdapter.submitData(lifecycle, data)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isLoading ->
                binding.progressBar.isVisible = isLoading
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

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                REFRESH_SUPPLIER -> {
                    supplierAdapter.refresh()
                    val snackbarValue = result.data?.getStringExtra(SNACKBAR_VALUE)
                    snackbarValue?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                            .setAction("Refresh") {
                                binding.rvSupplier.smoothScrollToPosition(0)
                            }.show()
                    }
                }
                REFRESH_UPDATED_SUPPLIER -> {
                    supplierAdapter.refresh()
                    val snackbarValue = result.data?.getStringExtra(SNACKBAR_VALUE)
                    snackbarValue?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).setAction("OK") {}
                            .show()
                    }
                }

                REFRESH_NOTFOUND_SUPPLIER -> {
                    supplierAdapter.refresh()
                    val snackbarValue = result.data?.getStringExtra(SNACKBAR_VALUE)
                    snackbarValue?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).setAction("OK") {}
                            .show()
                    }
                }
            }

        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupplierClicked(id: Int) {
    }

    override fun onEditClicked(id: Int) {
        val intent = Intent(requireActivity(), UpdateSupplierActivity::class.java)
        intent.putExtra(INTENT_SUPPLIER_ID, id)
        resultLauncher.launch(intent)
    }

    override fun onRemoveClicked(id: Int) {
        viewModel.deleteSupplier(token.tokenFormat(), id).observe(this) { event ->
            event.getContentIfNotHandled()?.let { resource ->
                when (resource) {
                    is Resource.Loading -> viewModel.setLoading(true)

                    is Resource.Success -> {
                        resource.data?.let { message ->
                            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
                                .setAction("OK") {}
                                .show()
                            supplierAdapter.refresh()
                            viewModel.setLoading(false)
                        }
                    }

                    else -> {
                        resource.message?.let { error ->
                            viewModel.setLoading(false)
                            if (error == STATUS_UNAUTHORIZED.toString()) {
                                Toast.makeText(requireContext(),
                                    "Session expired, need to login again!",
                                    Toast.LENGTH_SHORT).show()
                                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                                requireActivity().finish()
                            } else {
                                Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT)
                                    .setAction("OK") {}
                                    .show()
                                supplierAdapter.refresh()
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onTokenExpired() {
        Toast.makeText(requireContext(), "Session expired, need to login again!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_supplier -> {
                resultLauncher.launch(Intent(requireActivity(), CreateSupplierActivity::class.java))
            }
        }
    }
}