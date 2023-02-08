package com.mitocode.marketappmitocodegrupo2.presentation.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.domain.model.Product
import kotlinx.android.synthetic.main.item_product.view.*
import com.mitocode.marketappmitocodegrupo2.databinding.*
import com.squareup.picasso.Picasso

//1. Definir la lista
class ProductAdapter constructor(
    private var products : List<Product> = listOf(),
    private var onItemSelected : (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {



    //2. Definir ViewHolder
    //XML (LISTO)
    //Data (LISTO)
    inner class ViewHolder constructor(itemView:View) : RecyclerView.ViewHolder(itemView){

        private val binding : ItemProductBinding = ItemProductBinding.bind(itemView)

        fun bind(product:Product){

            Picasso.get().load(product.images[0]).error(R.drawable.icon_default_product).into(binding.imgProduct)

            binding.tvCode.text = product.code
            binding.tvDescription.text = product.description
            binding.tvPrice.text = "S/.${product.price}"

            binding.root.setOnClickListener {
                onItemSelected(product)
            }


        }

    }

    fun updateList(products : List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView:View =  LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

}