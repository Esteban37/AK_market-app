package com.mitocode.marketappmitocodegrupo2.presentation.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.core.BaseFragment
import com.mitocode.marketappmitocodegrupo2.data.model.AddressRequest
import com.mitocode.marketappmitocodegrupo2.data.model.PaymentMethodRequest
import com.mitocode.marketappmitocodegrupo2.data.model.ProductRequest
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterPurchaseRequest
import com.mitocode.marketappmitocodegrupo2.databinding.DialogPaymentSuccessBinding
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentPaymentBinding
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import com.mitocode.marketappmitocodegrupo2.presentation.common.gone
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import com.mitocode.marketappmitocodegrupo2.presentation.common.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : BaseFragment(R.layout.fragment_payment) {

    private lateinit var binding : FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val safeArgs: PaymentFragmentArgs by navArgs()
    private var directionType: Int = 1
    private var paymentType: Int = 1
    private val formatNumber = "%,.2f"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        init()
        setupObservables()
    }

    private fun init() = with(binding) {
        imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        constraintAddressTitle.setOnClickListener {
            if(constraintAddress.visibility == View.GONE) {
                imgArrowAddress.animate().rotation(-180f).setDuration(500).start()
            }else{
                imgArrowAddress.animate().rotation(0f).setDuration(500).start()
            }
            constraintAddress.visibility = if(constraintAddress.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        constraintDateTitle.setOnClickListener {
            if(constraintDate.visibility == View.GONE) {
                imgArrowDate.animate().rotation(-180f).setDuration(500).start()
            }else{
                imgArrowDate.animate().rotation(0f).setDuration(500).start()
            }
            constraintDate.visibility = if(constraintDate.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        constraintPaymentTypeTitle.setOnClickListener {
            if(constraintPaymentType.visibility == View.GONE) {
                imgArrowPaymentType.animate().rotation(-180f).setDuration(500).start()
            }else{
                imgArrowPaymentType.animate().rotation(0f).setDuration(500).start()
            }
            constraintPaymentType.visibility = if(constraintPaymentType.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        radioGroupAddress.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                rbHouse.id -> directionType = 1
                rbOffice.id -> directionType = 2
                rbOther.id -> directionType = 3
            }
        }

        radioGroupPaymentType.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                rbCY.id -> paymentType = 1
                rbCP.id -> paymentType = 2
                rbE.id -> paymentType = 3
            }
        }

        tvTotal.text = "S/. ${formatNumber.format(safeArgs.shoppingCartList.shoppingCartList.sumOf { it.total })}"

        btnPay.setOnClickListener {
            if(directionType == 0 || edtAddress.text!!.isEmpty() || edtReference.text!!.isEmpty() || edtDistrict.text!!.isEmpty() ||
                edtDate.text!!.isEmpty() || edtHour.text!!.isEmpty() || paymentType == 0 || edtTotalAmount.text!!.isEmpty()){
                showToast()
                return@setOnClickListener
            }

            viewModel.savePurchasedOrder(RegisterPurchaseRequest(
                AddressRequest(
                    directionType,
                    edtAddress.text.toString(),
                    edtReference.text.toString(),
                    edtDistrict.text.toString()
                ),
                PaymentMethodRequest(
                    paymentType,
                    edtTotalAmount.text.toString().toDouble()
                ),
                "${edtDate.text} ${edtHour.text}",
                safeArgs.shoppingCartList.shoppingCartList.map { it.toProductRequest() },
                safeArgs.shoppingCartList.shoppingCartList.sumOf { it.total }
            ))
        }

    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: RegisterPurchaseCreateState) {

        state?.isLoading?.let { condition ->
            if(condition) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
        state?.error?.let { error ->
            requireContext().toast(error)
        }
        state?.success?.let { reponse ->
            viewModel.deleteAllProducts(safeArgs.shoppingCartList.shoppingCartList)
            createDialogVersion(reponse).show()
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding){
        if (isLoading) progressBar.visible()
        else progressBar.gone()
    }

    private fun showToast(){
        requireContext().toast("Ingresa todo los campos requeridos")
    }

    private fun createDialogVersion(order: String): AlertDialog {

        val bindingAlert = DialogPaymentSuccessBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingAlert.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        bindingAlert.tvOrderNumber.text = order

        bindingAlert.btnAccept.setOnClickListener {
            alertDialog.dismiss()
            requireActivity().onBackPressed()
        }

        return alertDialog
    }

    private fun ShoppingCart.toProductRequest(): ProductRequest = ProductRequest(categoryCode, productId = productCode, amount)
    
}