package com.example.testtaskaeon.presentation.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testtaskaeon.R
import com.example.testtaskaeon.common.launchWhenStarted
import com.example.testtaskaeon.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding?.signInButton?.setOnClickListener {
            login()
        }

        binding?.password?.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
            }
            false
        }

        launchWhenStarted {
            viewModel.screenState.collect { state ->
                binding?.loading?.isVisible = state.isLoading
                binding?.signInButton?.isEnabled = !state.isLoading
            }
        }

        launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is LoginEffect.Error -> showError(effect.message)
                    LoginEffect.NavigateForward -> findNavController().navigate(R.id.action_loginFragment_to_paymentsListFragment)
                }
            }
        }
    }

    private fun showError(message: String) {
        binding?.let {
            Snackbar.make(
                it.root,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun login() {
        val login = binding?.login?.editText?.text.toString().trim()
        val password = binding?.password?.editText?.text.toString().trim()

        if (login.isNotBlank() && password.isNotBlank()) {
            viewModel.login(login, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}