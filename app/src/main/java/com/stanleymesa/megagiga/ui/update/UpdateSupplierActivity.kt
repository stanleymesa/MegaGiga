package com.stanleymesa.megagiga.ui.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.SupplierBody
import com.stanleymesa.core.utlis.*
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.ActivityUpdateSupplierBinding
import com.stanleymesa.megagiga.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateSupplierActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityUpdateSupplierBinding? = null
    private val binding get() = _binding!!
    private var token = ""
    private var supplierId = 0
    private val viewModel: UpdateSupplierViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        observeData()
        binding.toolbar.tvTitle.text = "Update Supplier"
        binding.toolbar.ivBack.isVisible = true
        binding.btnUpdate.setOnClickListener(this)
        binding.toolbar.ivBack.setOnClickListener(this)
    }

    private fun observeData() {
        viewModel.getTokenResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { token ->
                this.token = token
                Log.e("TOKEN", token)

                this.supplierId = intent.getIntExtra(INTENT_SUPPLIER_ID, 0)
                Log.e("ID", supplierId.toString())
                viewModel.getSupplierById(token.tokenFormat(), this.supplierId)
            }
        }

        viewModel.getSupplierByIdResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { resource ->
                when (resource) {
                    is Resource.Loading -> viewModel.setLoading(true)

                    is Resource.Success -> {
                        resource.data?.let { supplier ->
                            Log.e("SUPPLIER", supplier.toString())
                            binding.etSupplierName.setText(supplier.namaSupplier)
                            binding.etAddress.setText(supplier.alamat)
                            binding.etNoHandphone.setText(supplier.noTelp)
                            viewModel.setLoading(false)
                        }
                    }

                    else -> {
                        resource.message?.let { error ->
                            viewModel.setLoading(false)
                            if (error == STATUS_UNAUTHORIZED.toString()) {
                                Toast.makeText(this,
                                    "Session expired, need to login again!",
                                    Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else {
                                val intent = Intent()
                                intent.putExtra(SNACKBAR_VALUE, error)
                                setResult(REFRESH_NOTFOUND_SUPPLIER, intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }

        viewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled()?.let { isLoading ->
                binding.progressBar.isVisible = isLoading
                binding.btnUpdate.isEnabled = !isLoading
            }
        }
    }

    private fun isValidate(): Boolean {
        if (
            binding.etSupplierName.text.toString().isNotEmpty() &&
            binding.etAddress.text.toString().isNotEmpty() &&
            binding.etNoHandphone.text.toString().isNotEmpty() &&
            this.token.isNotEmpty()
        ) {
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_update -> {
                // Close Keyboard
                currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

                if (isValidate()) {
                    val supplierBody = SupplierBody(
                        namaSupplier = binding.etSupplierName.text.toString(),
                        alamat = binding.etAddress.text.toString(),
                        noTelp = binding.etNoHandphone.text.toString()
                    )

                    viewModel.updateSupplier(token.tokenFormat(), supplierId, supplierBody)
                        .observe(this) { event ->
                            event.getContentIfNotHandled()?.let { resource ->
                                when (resource) {
                                    is Resource.Loading -> viewModel.setLoading(true)

                                    is Resource.Success -> {
                                        resource.data?.let { message ->
                                            viewModel.setLoading(false)
                                            val intent = Intent()
                                            intent.putExtra(SNACKBAR_VALUE, message)
                                            setResult(REFRESH_UPDATED_SUPPLIER, intent)
                                            finish()
                                        }
                                    }

                                    else -> {
                                        resource.message?.let { error ->
                                            viewModel.setLoading(false)
                                            if (error == STATUS_UNAUTHORIZED.toString()) {
                                                Toast.makeText(this,
                                                    "Session expired, need to login again!",
                                                    Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this,
                                                    LoginActivity::class.java))
                                                finish()
                                            } else {
                                                Snackbar.make(binding.root,
                                                    error,
                                                    Snackbar.LENGTH_SHORT).setAction("OK") {}.show()
                                            }
                                        }
                                    }
                                }
                            }
                        }

                } else {
                    Snackbar.make(binding.root,
                        "Make sure not to leave any fields blank",
                        Snackbar.LENGTH_SHORT).setAction("OK") {}.show()
                }

            }

            R.id.iv_back -> finish()
        }
    }
}