package com.example.getirfinalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.databinding.ItemCardBinding

class SuggestedProductsAdapter(val bestSellerList: List<ProductItem>) : RecyclerView.Adapter<SuggestedProductsAdapter.SuggestedProductsViewHolder>() {

    inner class SuggestedProductsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        // val context = binding.root.context
        fun bind(product: ProductX) {
            with(binding) {
                tvPrice.text = product.priceText
                tvProductName.text = product.priceText
                ivCard.load(product.imageURL){
                    placeholder(R.drawable.basket)
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvAttribute.text = product.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedProductsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SuggestedProductsViewHolder(binding)
    }

    override fun getItemCount() = bestSellerList.size

    override fun onBindViewHolder(holder: SuggestedProductsViewHolder, position: Int) {
            holder.bind(product = bestSellerList[0].products[position])
    }


}