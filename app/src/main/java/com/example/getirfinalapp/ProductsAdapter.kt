package com.example.getirfinalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.databinding.ItemCardBinding

class ProductsAdapter(val bestSellerList: List<ProductModelItem>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        // val context = binding.root.context
        fun bind(product: ProductXX) {
            with(binding) {
                tvPrice.text = product.priceText
                tvProductName.text = product.name
                ivCard.load(product.imageURL){
                    placeholder(R.drawable.basket)
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvAttribute.text = product.attribute
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount() = bestSellerList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(product = bestSellerList[0].products[position])


    }


}