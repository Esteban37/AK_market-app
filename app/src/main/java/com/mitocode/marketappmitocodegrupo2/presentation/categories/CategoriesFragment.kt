package com.mitocode.marketappmitocodegrupo2.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.core.BaseAdapter
import com.mitocode.marketappmitocodegrupo2.core.BaseFragment
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentCategoriesBinding
import com.mitocode.marketappmitocodegrupo2.databinding.ItemCategoryBinding
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : BaseFragment(R.layout.fragment_categories) {

    private val viewModel : CategoriesViewModel by viewModels()

    private lateinit var binding : FragmentCategoriesBinding

    //private lateinit var adapter : CategoryAdapter

    private val adapter : BaseAdapter<Category> = object : BaseAdapter<Category>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapter.BaseViewHolder<Category> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            return object : BaseAdapter.BaseViewHolder<Category>(view) {
                private val binding: ItemCategoryBinding = ItemCategoryBinding.bind(itemView)
                override fun bind(entity: Category) {
                    Picasso.get().load(entity.cover).into(binding.imgCategory)
                    binding.root.setOnClickListener {
                        val uuid = entity.uuid

                        val directions = CategoriesFragmentDirections.actionCategoriesFragmentToDetailCategoryFragment(uuid)
                        navigateToDirections(directions)
                        //Navigation.findNavController(binding.root).navigate(directions)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)

        setupAdapter()
        //viewModel.populateCategories()
        viewModel.onUIReady()


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect(){
                    updateUI(it)
                }
            }
        }



    }

    private fun setupAdapter() {
        /*adapter = CategoryAdapter(){ category ->
            val uuid = category.uuid
            val directions = CategoriesFragmentDirections.actionCategoriesFragmentToDetailCategoryFragment(uuid)
            Navigation.findNavController(binding.root).navigate(directions)
        }*/
        binding.rvCategories.adapter = adapter
    }

    private fun updateUI(state: CategoryState) {
        state?.isLoading?.let { condition ->
            if(condition) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
        state?.error?.let { error ->
            requireContext().toast(error)
        }
        state?.categories?.let { categories ->
           adapter.updateList(categories)
        }
    }

}