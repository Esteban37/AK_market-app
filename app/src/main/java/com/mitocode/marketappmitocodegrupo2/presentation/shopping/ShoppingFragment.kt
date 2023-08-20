package com.mitocode.marketappmitocodegrupo2.presentation.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.core.BaseAdapter
import com.mitocode.marketappmitocodegrupo2.core.BaseFragment
import com.mitocode.marketappmitocodegrupo2.databinding.BaseDialogBinding
import com.mitocode.marketappmitocodegrupo2.databinding.DialogUpdateProductBinding
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentShoppingBinding
import com.mitocode.marketappmitocodegrupo2.databinding.ItemPurchasedProductBinding
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCartList
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingFragment : BaseFragment(R.layout.fragment_shopping) {

    private lateinit var binding : FragmentShoppingBinding
    private val viewModel: ShoppingViewModel by viewModels()
    private val formatNumber = "%,.2f"
    private var shoppingCartList: List<ShoppingCart> = listOf()

    private val adapter: BaseAdapter<ShoppingCart> = object : BaseAdapter<ShoppingCart>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<ShoppingCart> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchased_product, parent, false)
            return object: BaseViewHolder<ShoppingCart>(view){

                private val binding: ItemPurchasedProductBinding = ItemPurchasedProductBinding.bind(itemView)

                override fun bind(entity: ShoppingCart) = with(binding) {
                    Picasso.get().load(entity.image).error(R.drawable.empty).into(binding.imgProduct)
                    tvProductDescription.text = entity.productDescription
                    tvQuantity.text = entity.amount.toString()
                    tvPrice.text = entity.price.toString()
                    tvTotal.text = "S/. ${formatNumber.format(entity.total)}"

                    imgRemoveProduct.setOnClickListener {
                        viewModel.deleteProduct(entity)
                    }

                    imgEditProduct.setOnClickListener {
                        updatePurchasedProductDialog(entity).show()
                    }
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentShoppingBinding.bind(view)

        init()
        setupAdapter()
        setupObservers()
    }

    private fun init() {
        viewModel.loadPurchasedProducts()
        binding.imgDeleteAll.setOnClickListener {
            if(!shoppingCartList.any()) {
                requireContext().toast("No tienes productos para eliminar")
                return@setOnClickListener
            }
            deleteAllProductsDialog().show()
        }

        binding.btnCheckIn.setOnClickListener {
            val directions = ShoppingFragmentDirections.actionShoppingFragmentToPaymentFragment(ShoppingCartList(shoppingCartList))
            Navigation.findNavController(binding.root).navigate(directions)
        }
    }

    private fun setupAdapter() = with(binding){
        rvProduct.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(state: ShoppingViewModel.OrdersState) {
        when(state){
            ShoppingViewModel.OrdersState.Init -> Unit
            is ShoppingViewModel.OrdersState.Error -> requireContext().toast(state.rawResponse)
            is ShoppingViewModel.OrdersState.IsLoading -> showProgress(state.isLoading)
            is ShoppingViewModel.OrdersState.Success -> {
                checkPurchasedProduct(state)
            }
            is ShoppingViewModel.OrdersState.Amount -> {
                binding.tvTotal.text = "S/. ${formatNumber.format(state.amount)}"
            }
        }
    }

    private fun checkPurchasedProduct(state: ShoppingViewModel.OrdersState.Success) {
        if (state.purchasedProducts.any()) {
            shoppingCartList = state.purchasedProducts
            adapter.updateList(state.purchasedProducts)
            binding.btnCheckIn.isEnabled = true
            binding.rvProduct.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE

        } else {
            binding.btnCheckIn.isEnabled = false
            binding.rvProduct.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        }
    }

    private fun updatePurchasedProductDialog(purchasedProduct: ShoppingCart): AlertDialog {

        val bindingAlert = DialogUpdateProductBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingAlert.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        bindingAlert.edtAmount.setText(purchasedProduct.amount.toString())

        bindingAlert.btnUpdate.setOnClickListener {
            val amount = bindingAlert.edtAmount.text.toString().toInt()
            if (amount > 0){
                purchasedProduct.amount = amount
                purchasedProduct.total = amount*purchasedProduct.price
                viewModel.updateProduct(purchasedProduct)
                alertDialog.dismiss()
            }else{
                requireContext().toast("Cantidad ingresada no valida")
            }

        }

        bindingAlert.btnClose.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun deleteAllProductsDialog(): AlertDialog {

        val bindingAlert = BaseDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingAlert.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        bindingAlert.btnAccept.text = "Eliminar"
        bindingAlert.btnCancel.text = "Cancelar"
        bindingAlert.tvMessage.text = "¿Estás seguro de eliminar todos los productos de la lista?"

        bindingAlert.btnAccept.setOnClickListener {
            viewModel.deleteAllProducts(shoppingCartList)
            alertDialog.dismiss()
        }

        bindingAlert.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun showProgress(visibility: Boolean) = with(binding) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}