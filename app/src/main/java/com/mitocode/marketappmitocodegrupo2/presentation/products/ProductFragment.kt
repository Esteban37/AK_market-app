package com.mitocode.marketappmitocodegrupo2.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.core.BaseAdapter
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentDetailCategoryBinding
import com.mitocode.marketappmitocodegrupo2.databinding.ItemCategoryBinding
import com.mitocode.marketappmitocodegrupo2.databinding.ItemProductBinding
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import com.mitocode.marketappmitocodegrupo2.domain.model.Product
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_detail_category) {

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }*/

    private val arguments : ProductFragmentArgs by navArgs()
    private lateinit var binding : FragmentDetailCategoryBinding
    private val viewModel : ProductViewModel by viewModels()
    //private lateinit var adapter : ProductAdapter

    private val adapter : BaseAdapter<Product> = object : BaseAdapter<Product>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapter.BaseViewHolder<Product> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return object : BaseAdapter.BaseViewHolder<Product>(view) {
                private val binding: ItemProductBinding = ItemProductBinding.bind(itemView)
                override fun bind(entity: Product) {

                    Picasso.get().load(entity.images[0]).error(R.drawable.icon_default_product).into(binding.imgProduct)

                    binding.tvCode.text = entity.code
                    binding.tvDescription.text = entity.description
                    binding.tvPrice.text = "S/.${entity.price}"

                    binding.root.setOnClickListener {
                        val directions = ProductFragmentDirections.actionDetailCategoryFragmentToDetailProductFragment(arguments.categoryId, entity)
                        Navigation.findNavController(binding.root).navigate(directions)
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailCategoryBinding.bind(view)

        init()
        setupAdapter()
        collectData()
    }

    private fun setupAdapter() {
        /*adapter = ProductAdapter(){ product ->
            val directions = ProductFragmentDirections.actionDetailCategoryFragmentToDetailProductFragment(product)
            Navigation.findNavController(binding.root).navigate(directions)
        }*/
        binding.rvProduct.adapter = adapter

    }

    private fun collectData() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    updateUI(it)
                }
            }
        }

    }

    private fun updateUI(state: ProductState) {
        state?.isLoading?.let { condition ->
            if(condition) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        state?.error?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        state?.products?.let { products ->
            adapter.updateList(products)
        }
    }

    private fun init() {

        arguments.categoryId?.let { uuid ->
            viewModel.getProducts(uuid)
        }

    }


}