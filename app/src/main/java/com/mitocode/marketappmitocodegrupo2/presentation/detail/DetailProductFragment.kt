package com.mitocode.marketappmitocodegrupo2.presentation.detail

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.core.BaseAdapter
import com.mitocode.marketappmitocodegrupo2.core.BaseFragment
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterCategoryRequest
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentDetailProductBinding
import com.mitocode.marketappmitocodegrupo2.databinding.ItemProductImageBinding
import com.mitocode.marketappmitocodegrupo2.domain.model.Product
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import com.mitocode.marketappmitocodegrupo2.presentation.common.gone
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import com.mitocode.marketappmitocodegrupo2.presentation.common.visible
import com.mitocode.marketappmitocodegrupo2.util.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductFragment : BaseFragment(R.layout.fragment_detail_product) {

    private lateinit var binding : FragmentDetailProductBinding
    private val detailProductViewModel: DetailProductViewModel by viewModels()
    private val arguments : DetailProductFragmentArgs by navArgs()

    private val adapter : BaseAdapter<String> = object : BaseAdapter<String>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapter.BaseViewHolder<String> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_image, parent, false)
            return object : BaseAdapter.BaseViewHolder<String>(view) {
                private val binding: ItemProductImageBinding = ItemProductImageBinding.bind(itemView)
                override fun bind(image: String) {

                    Picasso.get().load(image).error(R.drawable.icon_default_product).into(binding.imgProduct)
                    binding.root.setOnClickListener {
                        Picasso.get().load(image).error(R.drawable.icon_default_product).into(imgProduct)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailProductViewModel.number.observe(this){ number ->
            tvQuantityProducts.text = "$number"
            if (number == 0){
                imgRemoveProduct.isEnabled = false
                //imgRemoveProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
                btnAdd.isEnabled = false
            } else{
                imgRemoveProduct.isEnabled = true
                //imgRemoveProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                btnAdd.isEnabled = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailProductBinding.bind(view)

        init()
        events()
        setupAdapter()
        setupObservables()
    }

    private fun init() {

        arguments.product?.let { product ->
            //Log.i("DetailProductFragment", "Product -> " + product)

            tvDescription.text = product.description
            tvPrice.text = "S/.${product.price}"
            tvFeautures.text = product.feautures

            Picasso.get().load(product.images[0]).error(R.drawable.icon_default_product).into(imgProduct)

            adapter.updateList(product.images)
        }

    }

    private fun events() = with(binding) {

        imgAddProduct.setOnClickListener {
            detailProductViewModel.addItem()
        }

        imgRemoveProduct.setOnClickListener {
            detailProductViewModel.subtractItem()
        }

        btnAdd.setOnClickListener {

            var categoryCode:String = ""
            var productCode:String = ""
            var productDescription:String = ""
            var price:Double = 0.0
            var image:String = ""

            arguments.categoryId?.let { uuid ->
                categoryCode = uuid
            }
            arguments.product?.let { product ->
                productCode = product.code
                productDescription = product.description
                price = product.price
                image = product.images[0]
            }

            detailProductViewModel.savePurchase(ShoppingCart(0, categoryCode, productCode, productDescription, tvQuantityProducts.text.toString().toInt(), price, image, price * tvQuantityProducts.text.toString().toInt()))
        }

    }

    private fun setupAdapter() = with(binding) {
        rvProductImages.adapter = adapter
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                detailProductViewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: DetailProductViewModel.RegisterPurchaseState) {
        when(state){
            DetailProductViewModel.RegisterPurchaseState.Init -> Unit
            is DetailProductViewModel.RegisterPurchaseState.Error -> requireContext().toast(state.rawResponse)
            is DetailProductViewModel.RegisterPurchaseState.IsLoading -> handlerLoading(state.isLoading)
            is DetailProductViewModel.RegisterPurchaseState.Success -> {
                requireContext().toast(state.response)
                requireActivity().onBackPressed()
            }
        }
    }

    private fun handlerLoading(isLoading: Boolean) = with(binding){
        if (isLoading) progressBar.visible()
        else progressBar.gone()
    }

}
