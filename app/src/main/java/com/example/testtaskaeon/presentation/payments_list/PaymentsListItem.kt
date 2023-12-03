package com.example.testtaskaeon.presentation.payments_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.testtaskaeon.R
import com.example.testtaskaeon.databinding.FragmentPaymentsListItemBinding
import com.example.testtaskaeon.domain.model.Payment
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import java.time.format.DateTimeFormatter

class PaymentsListItem(
    val payment: Payment,
    val context: Context
) : AbstractBindingItem<FragmentPaymentsListItemBinding>() {
    override val type: Int
        get() = R.id.fragment_payments_list_item

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentPaymentsListItemBinding {
        return FragmentPaymentsListItemBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: FragmentPaymentsListItemBinding, payloads: List<Any>) {
        binding.title.text = payment.title
        binding.detailsLayout.isVisible = payment.amount != null || payment.created != null
        if (!payment.amount.isNullOrBlank()) {
            binding.amount.text = context.getString(R.string.payment_amount, payment.amount)
        }
        binding.date.text = payment.created?.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
    }
}