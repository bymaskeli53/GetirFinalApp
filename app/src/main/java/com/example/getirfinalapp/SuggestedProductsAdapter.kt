package com.example.getirfinalapp

import android.graphics.Paint
import coil.load
import coil.transform.CircleCropTransformation

class SuggestedProductsAdapter(
    val listener: AddItemClickListener<ProductX>,
    items: List<ProductX>,
    val onItemClick: (position: Int) -> Unit
) : BaseProductsAdapter<ProductX>(listener, items) {

    override fun onBindItem(holder: BaseProductsViewHolder, item: ProductX, position: Int) {
        with(holder.binding) {
            tvPrice.text = item.priceText
            tvProductName.paintFlags = tvProductName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            tvProductName.text = item.category
            ivCard.load(item.squareThumbnailURL ?: item.imageURL) {
                placeholder(R.drawable.basket)
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvAttribute.text = item.name

            ivPlus.setOnClickListener {
                listener.onAddItemClick(item)
               // notifyItemChanged(position)
            }
        }

        holder.itemView.setOnClickListener { onItemClick(position) }
    }
}
