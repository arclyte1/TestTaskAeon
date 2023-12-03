package com.example.testtaskaeon.presentation.payments_list

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testtaskaeon.R
import com.example.testtaskaeon.common.launchWhenStarted
import com.example.testtaskaeon.databinding.FragmentPaymentsListBinding
import com.example.testtaskaeon.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsListFragment : Fragment(R.layout.fragment_payments_list) {

    private var binding: FragmentPaymentsListBinding? = null
    private val viewModel: PaymentsListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentsListBinding.bind(view)

        val paymentsAdapter = ItemAdapter<PaymentsListItem>()
        val paymentsFastAdapter = FastAdapter.with(paymentsAdapter)
        binding?.list?.adapter = paymentsFastAdapter

        binding?.refreshLayout?.setOnRefreshListener {
            viewModel.refreshPaymentList()
        }

        binding?.menuButton?.setOnClickListener(::showMenu)

        launchWhenStarted {
            viewModel.screenState.collect { state ->
                paymentsAdapter.set(state.paymentList.map { PaymentsListItem(it, requireContext()) })
                binding?.refreshLayout?.isRefreshing = state.isLoading
            }
        }

        launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is PaymentsListEffect.Error -> showError(effect.message)
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

    private fun showMenu(v: View) {
        val popup = PopupMenu(requireActivity(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.payments_list_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_logout -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}