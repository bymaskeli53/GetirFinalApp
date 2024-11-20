package com.example.getirfinalapp.adapter

import android.graphics.Paint
import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.data.model.SuggestedProductItem
import com.example.getirfinalapp.R

class SuggestedProductsAdapter(
    val listener: AddItemClickListener<SuggestedProductItem>,
    items: List<SuggestedProductItem>,
    val onItemClick: (position: Int) -> Unit
) : BaseProductsAdapter<SuggestedProductItem>(listener, items) {

    override fun onBindItem(holder: BaseProductsViewHolder, item: SuggestedProductItem, position: Int) {
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
            }
        }

        holder.itemView.setOnClickListener { onItemClick(position) }
    }
}
