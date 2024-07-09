package com.example.getirfinalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.databinding.ItemCardBinding

class SuggestedProductsAdapter(private val listener: AddItemClickListener, val productItems: List<ProductItem>, val onItemClick: (position : Int) -> Unit) :
    RecyclerView.Adapter<SuggestedProductsAdapter.SuggestedProductsViewHolder>() {

    inner class SuggestedProductsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        // val context = binding.root.context
        fun bind(product: ProductX) {
            with(binding) {
                tvPrice.text = product.priceText
                tvProductName.text = product.category
                ivCard.load(product.imageURL){
                    placeholder(R.drawable.basket)
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvAttribute.text = product.name

                ivPlus.setOnClickListener {
                        listener.onAddItemClick(product)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedProductsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SuggestedProductsViewHolder(binding)
    }

    override fun getItemCount() = productItems[0].products.size

    override fun onBindViewHolder(holder: SuggestedProductsViewHolder, position: Int) {
            holder.bind(product = productItems[0].products[position])
            holder.itemView.setOnClickListener { onItemClick(position) }
    }

    interface AddItemClickListener {
        fun onAddItemClick(product: ProductX)

    }


}