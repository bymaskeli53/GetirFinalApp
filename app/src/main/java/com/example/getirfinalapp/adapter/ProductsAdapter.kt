package com.example.getirfinalapp.adapter

import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.data.model.GeneralProductItem
import com.example.getirfinalapp.R

class ProductsAdapter(
    val listener: AddItemClickListener<GeneralProductItem>,
    items: List<GeneralProductItem>
) : BaseProductsAdapter<GeneralProductItem>(listener, items) {

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
