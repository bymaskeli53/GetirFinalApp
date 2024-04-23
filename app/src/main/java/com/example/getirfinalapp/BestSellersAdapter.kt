package com.example.getirfinalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.getirfinalapp.databinding.ItemCardBinding

class BestSellersAdapter(val bestSellerList: List<Product>) : RecyclerView.Adapter<BestSellersAdapter.BestSellersViewHolder>() {

    inner class BestSellersViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellersViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BestSellersViewHolder(binding)
    }

    override fun getItemCount() = bestSellerList.size

    override fun onBindViewHolder(holder: BestSellersViewHolder, position: Int) {
            holder.bind(product = bestSellerList[position])
    }


}