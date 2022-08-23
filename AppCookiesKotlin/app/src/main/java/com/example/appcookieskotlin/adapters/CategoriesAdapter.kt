package com.example.appcookieskotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.databinding.CategoryItemsBinding
import com.example.appcookieskotlin.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.MyHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null

    inner class MyHolder(var binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    fun setCategoryList(categoriesList: List<Category>) {
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategorieName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


}