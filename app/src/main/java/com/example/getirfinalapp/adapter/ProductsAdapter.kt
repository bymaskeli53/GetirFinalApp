package com.example.getirfinalapp.adapter

import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.R
import com.example.getirfinalapp.data.model.GeneralProductItem

class ProductsAdapter(
    val listener: AddItemClickListener<GeneralProductItem>,
    items: List<GeneralProductItem>
) : BaseProductsAdapter<GeneralProductItem>(items) {

    override fun onBindItem(holder: BaseProductsViewHolder, item: GeneralProductItem, position: Int) {
        with(holder.binding) {
            tvPrice.text = item.priceText
            tvProductName.text = item.name
            ivCard.load(item.imageURL) {
                placeholder(R.drawable.basket)
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvAttribute.text = item.attribute

            ivPlus.setOnClickListener {
                listener.onAddItemClick(item)
            }
        }
    }
}
