package com.example.appcookieskotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appcookieskotlin.databinding.MealItemBinding
import com.example.appcookieskotlin.pojo.MealsByCategory

class CateforyMealAdapter: RecyclerView.Adapter<CateforyMealAdapter.MyHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList:List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class MyHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)
            , parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }


}