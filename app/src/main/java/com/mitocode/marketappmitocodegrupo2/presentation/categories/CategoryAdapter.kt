package com.mitocode.marketappmitocodegrupo2.presentation.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import com.squareup.picasso.Picasso
import com.mitocode.marketappmitocodegrupo2.databinding.*

//1. Definir donde esta la lista con la informacion
//3. Implementar los metodos del adapter
class CategoryAdapter constructor(
    var categories:List<Category> = listOf(),
    var onItemClick:(Category)->Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()  {

    // () -> Bollean

    //2. Definir el ViewHolder
    //XML donde pinta la informacion (Listo)
    //Informacion
    inner class ViewHolder constructor(itemView:View) : RecyclerView.ViewHolder(itemView) {

        private val binding : ItemCategoryBinding = ItemCategoryBinding.bind(itemView)

        fun bind(category: Category){

            //Picasso, Glide, Coil
            Picasso.get().load(category.cover).error(R.drawable.ic_launcher_background).into(binding.imgCategory)
            binding.tvUuid.text = category.uuid

            binding.root.setOnClickListener {
                onItemClick(category)
            }

        }

    }

    fun updateList(categories:List<Category>){
        this.categories = categories
        notifyDataSetChanged()
    }

    //5. //XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return ViewHolder(itemView)
    }

    //Informacion
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]  //Deporte
        holder.bind(category)
    }

    //4. Cuantos elementos tiene mi lista
    override fun getItemCount(): Int {
        return categories.size
    }
}