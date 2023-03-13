package com.stanleymesa.megagiga.ui.login

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.stanleymesa.core.data.Resource
import com.stanleymesa.core.domain.body.RegisterBody
import com.stanleymesa.core.utlis.getColorFromAttr
import com.stanleymesa.megagiga.R
import com.stanleymesa.megagiga.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setTextColor()
        observeData()
        binding.btnRegister.setOnClickListener(this)
    }

    private fun setTextColor() {
        val colorStroke = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_focused),
            ),

            intArrayOf(
                requireActivity().getColorFromAttr(com.google.android.material.R.attr.colorOnSecondary),
                requireActivity().getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
            )
        )

        binding.tiUsername.apply {
            setBoxStrokeColorStateList(colorStroke)
            defaultHintTextColor = colorStroke
            hintTextColor = colorStroke
        }

        binding.tiProfileName.apply {
            setBoxStrokeColorStateList(colorStroke)
            defaultHintTextColor = colorStroke
            hintTextColor = colorStroke
        }

        binding.tiPassword.apply {
            setBoxStrokeColorStateList(colorStroke)
            defaultHintTextColor = colorStroke
            hintTextColor = colorStroke
        }
    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                // Close Keyboard
                requireActivity().currentFocus?.let { view ->
                    val imm =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

                val registerBody = RegisterBody(
                    username = binding.etUsername.text.toString(),
                    profileName = binding.etProfileName.text.toString(),
                    password = binding.etPassword.text.toString()
                )
                viewModel.register(registerBody).observe(viewLifecycleOwner) { event ->
                    event.getContentIfNotHandled()?.let { resource ->
                        when (resource) {
                            is Resource.Loading -> viewModel.setLoading(true)

                            is Resource.Success -> {
                                resource.data?.let {
                                    Snackbar.make(binding.root,
                                        "Register Success! You need to login to continue",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("OK") {}.show()
                                }
                                viewModel.setLoading(false)
                            }

                            else -> {
                                resource.message?.let { error ->
                                    Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT)
                                        .setAction("OK") {}.show()
                                }
                                viewModel.setLoading(false)
                            }
                        }
                    }
                }
            }
        }
    }

}