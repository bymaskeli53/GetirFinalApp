package com.example.getirfinalapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getirfinalapp.data.model.GeneralProductItem
import com.example.getirfinalapp.databinding.ItemBasketBinding

class BasketAdapter(val productsInBasket: List<GeneralProductItem>) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    inner class BasketViewHolder(val binding: ItemBasketBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productsInBasket.size
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.binding.tvBasket.text = productsInBasket[position].name
        holder.binding.tvPrice.text = productsInBasket[position].price.toString()
        holder.binding.tvQuantity.text = productsInBasket[position].quantity.toString()
    }
}
