package com.stanleymesa.megagiga.ui.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.SupplierBody
import com.stanleymesa.core.domain.body.UpdateProductBody
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.utlis.*
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.ActivityUpdateProductBinding
import com.stanleymesa.megagiga.ui.login.LoginActivity
import com.stanleymesa.megagiga.ui.supplier.SupplierActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProductActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityUpdateProductBinding? = null
    private val binding get() = _binding!!
    private var token = ""
    private var supplier: Supplier? = null
    private var productId = 0
    private val viewModel: UpdateProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        observeData()
        binding.toolbar.tvTitle.text = "Update Product"
        binding.toolbar.ivBack.isVisible = true
        binding.btnUpdate.setOnClickListener(this)
        binding.layoutSupplier.setOnClickListener(this)
        binding.toolbar.ivBack.setOnClickListener(this)
    }

    private fun observeData() {

        viewModel.getTokenResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { token ->
                this.token = token

                this.productId = intent.getIntExtra(INTENT_PRODUCT_ID, 0)
                viewModel.getProductById(token.tokenFormat(), productId)
            }
        }

        viewModel.getProductByIdResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { resource ->
                when (resource) {
                    is Resource.Loading -> viewModel.setLoading(true)

                    is Resource.Success -> {
                        resource.data?.let { product ->
                            binding.etProductName.setText(product.namaBarang)
                            binding.etPrice.setText(product.harga.toInt().toString())
                            binding.etStock.setText(product.stok.toString())

                            product.supplier?.let { supplier ->
                                this.supplier = supplier
                                binding.contentSupplier.apply {
                                    root.isVisible = true
                                    tvId.text = supplier.id.toString()
                                    tvSupplierName.text = supplier.namaSupplier
                                    tvSupplierHome.text = supplier.alamat
                                    tvSupplierPhone.text = supplier.noTelp
                                }
                            }
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
                                setResult(REFRESH_NOTFOUND_PRODUCT, intent)
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

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == GET_SUPPLIER) {
                val supplier = result.data?.getParcelableExtra<Supplier>(INTENT_SUPPLIER)
                supplier?.let {
                    this.supplier = it
                    binding.contentSupplier.apply {
                        root.isVisible = true
                        tvId.text = it.id.toString()
                        tvSupplierName.text = it.namaSupplier
                        tvSupplierHome.text = it.alamat
                        tvSupplierPhone.text = it.noTelp
                    }
                }
            }
        }

    private fun isValidate(): Boolean {
        if (
            binding.etProductName.text.toString().isNotEmpty() &&
            binding.etPrice.text.toString().isNotEmpty() &&
            binding.etStock.text.toString().isNotEmpty() &&
            this.supplier != null &&
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
                        namaSupplier = this.supplier!!.namaSupplier,
                        alamat = this.supplier!!.alamat,
                        noTelp = this.supplier!!.noTelp
                    )

                    val updateProductBody = UpdateProductBody(
                        namaBarang = binding.etProductName.text.toString(),
                        harga = binding.etPrice.text.toString().toDouble(),
                        stok = binding.etStock.text.toString().toInt(),
                        supplier = supplierBody
                    )

                    viewModel.updateProduct(token.tokenFormat(), productId, updateProductBody)
                        .observe(this) { event ->
                            event.getContentIfNotHandled()?.let { resource ->
                                when (resource) {
                                    is Resource.Loading -> viewModel.setLoading(true)

                                    is Resource.Success -> {
                                        resource.data?.let { message ->
                                            viewModel.setLoading(false)
                                            val intent = Intent()
                                            intent.putExtra(SNACKBAR_VALUE, message)
                                            setResult(REFRESH_UPDATED_PRODUCT, intent)
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

            R.id.layout_supplier -> {
                resultLauncher.launch(Intent(this, SupplierActivity::class.java))
            }

            R.id.iv_back -> finish()
        }
    }

}