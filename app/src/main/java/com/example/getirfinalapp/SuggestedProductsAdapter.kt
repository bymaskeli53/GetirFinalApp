package com.example.getirfinalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getirfinalapp.databinding.ItemCardBinding

class SuggestedProductsAdapter(val bestSellerList: List<Product>) : RecyclerView.Adapter<SuggestedProductsAdapter.SuggestedProductsViewHolder>() {

    inner class SuggestedProductsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        // val context = binding.root.context
        fun bind(product: Product) {
            with(binding) {
                tvPrice.text = product.productPrice
                tvProductName.text = product.productName
                ivCard.setImageResource(R.drawable.basket)
                tvAttribute.text = product.productAttribute
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedProductsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SuggestedProductsViewHolder(binding)
    }

    override fun getItemCount() = bestSellerList.size

    override fun onBindViewHolder(holder: SuggestedProductsViewHolder, position: Int) {
            holder.bind(product = bestSellerList[position])
    }


}